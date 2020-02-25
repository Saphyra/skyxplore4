package com.github.saphyra.skyxplore_deprecated.game.dao.common.cache;

import com.github.saphyra.skyxplore.app.common.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class CacheRepository<KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> implements CrudRepository<ENTITY, ID> {
    private static final int MAX_CHUNK_SIZE = 10000;

    private final ConcurrentHashMap<KEY, ExpirableEntity<ConcurrentHashMap<ID, ModifiableEntity<ENTITY>>>> cacheMap = new ConcurrentHashMap<>();
    private final Set<ID> deleteQueue = ConcurrentHashMap.newKeySet();

    private final String entityName;
    protected final REPOSITORY repository;
    private final Function<ENTITY, KEY> keyMapper;
    private final CacheContext cacheContext;

    protected CacheRepository(REPOSITORY repository, Function<ENTITY, KEY> keyMapper, CacheContext cacheContext) {
        this.repository = repository;
        this.keyMapper = keyMapper;
        this.cacheContext = cacheContext;
        this.entityName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
    }

    protected abstract List<ENTITY> getByKey(KEY key);

    protected abstract void deleteByIds(List<ID> ids);

    protected void deleteByKey(KEY key) {
        log.debug("Deleting {}(s) for key {}", entityName, key);
        List<ENTITY> entities = getByKey(key);
        log.debug("Number of {}(s) to delete: {}", entities, entities.size());
        deleteAll(entities);
    }

    protected Map<ID, ENTITY> getMapByKey(KEY key) {
        log.debug("Querying {}(s) by key {}", entityName, key);
        return extractEntities(getMap(key));
    }

    private ConcurrentHashMap<ID, ENTITY> extractEntities(ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> map) {
        Map<ID, ENTITY> mapping = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, idModifiableEntityEntry -> idModifiableEntityEntry.getValue().getEntity()));
        return new ConcurrentHashMap<>(mapping);
    }

    public void fullSync() {
        log.info("Executing full-sync for entity {}...", entityName);
        processDeletions();
        syncChanges();
        evictExpiredEntities();
    }

    public void evictExpiredEntities() {
        synchronized (cacheMap) {
            log.info("Evicting expired entities for entity {}...", entityName);
            List<KEY> expiredKeys = cacheMap.entrySet()
                .stream()
                .filter(expirableEntityEntry -> expirableEntityEntry.getValue().isExpired())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
            expiredKeys.forEach(cacheMap::remove);
            log.info("Evicting expired entities finished for entity {}. Number of expired keys: {}", entityName, expiredKeys.size());
        }
    }

    public void syncChanges() {
        log.info("Synchronizing modifications for entity {}...", entityName);
        int synchedEntitiesAmount = cacheMap.values()
            .stream()
            .map(map -> {
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (map) {
                    List<ENTITY> entities = filterModified(map.getEntity());
                    repository.saveAll(entities);
                    entities.forEach(entity -> entity.setNew(false));
                    return entities.size();
                }
            })
            .mapToInt(Integer::intValue)
            .sum();
        log.info("Synchronization finished for entity {}. Updated entities: {}", entityName, synchedEntitiesAmount);
    }

    private List<ENTITY> filterModified(ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> map) {
        return map.values()
            .stream()
            .filter(ModifiableEntity::isModified)
            .map(ModifiableEntity::getEntity)
            .collect(Collectors.toList());
    }

    public void processDeletions() {
        synchronized (deleteQueue) {
            log.info("Processing deletions for entity {}...", entityName);
            ArrayList<ID> ids = new ArrayList<>(deleteQueue);
            chunks(ids).forEach(this::deleteByIds);
            deleteQueue.clear();
            log.info("Deletion finished for entity {}. Number of deleted entities: {}", entityName, ids.size());
        }
    }

    private static <T> List<List<T>> chunks(List<T> bigList) {
        List<List<T>> chunks = new ArrayList<>();

        for (int i = 0; i < bigList.size(); i += CacheRepository.MAX_CHUNK_SIZE) {
            List<T> chunk = bigList.subList(i, Math.min(bigList.size(), i + CacheRepository.MAX_CHUNK_SIZE));
            chunks.add(chunk);
        }
        log.debug("Number of entities {} was chunked to {} parts.", bigList.size(), chunks.size());
        return chunks;
    }

    @Override
    public <S extends ENTITY> S save(S entity) {
        log.debug("Saving entity {}", entity);
        getMap(keyMapper.apply(entity)).put(Objects.requireNonNull(entity.getId()), new ModifiableEntity<>(entity, true));
        deleteQueue.remove(entity.getId());
        return entity;
    }

    private ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> getMap(KEY key) {
        if (!cacheMap.containsKey(key)) {
            log.debug("Cache does not contain key {}. Loading entities...", key);
            loadByKey(key);
        }
        return cacheMap.get(key).updateLastAccessAndGetEntity();

    }

    @Override
    public <S extends ENTITY> Iterable<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(this::save);
        return iterable;
    }

    @Override
    public Optional<ENTITY> findById(ID id) {
        if (deleteQueue.contains(id)) {
            log.debug("Entity {} with id {} is already deleted.", entityName, id);
            return Optional.empty();
        }
        Optional<ENTITY> cachedEntity = cacheMap.values()
            .stream()
            .map(ExpirableEntity::getEntity)
            .flatMap(map -> extractEntities(map).values().stream())
            .filter(entity -> Objects.equals(entity.getId(), id))
            .findAny();

        cachedEntity.ifPresent(entity -> cacheMap.get(keyMapper.apply(entity)).updateLastAccess());
        return cachedEntity.isPresent() ? cachedEntity : searchInRepository(id);
    }

    private Optional<ENTITY> searchInRepository(ID id) {
        log.debug("Entity {} is not found in cache with id {}. Searching in repository...", entityName, id);
        Optional<ENTITY> entityOptional = repository.findById(id);
        entityOptional.ifPresent(entity -> {
            log.debug("Entity {} is found in repository with id {}. Loading to cache...", entityName, id);
            loadByKey(keyMapper.apply(entity));
        });
        return entityOptional;
    }

    @Override
    public boolean existsById(ID id) {
        if (deleteQueue.contains(id)) {
            log.debug("Entity {} with id {} is deleted.", entityName, id);
            return false;
        }
        return repository.existsById(id);
    }

    @Override
    public Iterable<ENTITY> findAll() {
        Iterable<ENTITY> entities = repository.findAll();
        return CollectionUtil.toList(entities).stream()
            .filter(entity -> !deleteQueue.contains(entity.getId()))
            .collect(Collectors.groupingBy(keyMapper))
            .entrySet()
            .stream()
            .peek(entry -> addToCache(entry.getKey(), entry.getValue()))
            .flatMap(entry -> entry.getValue().stream())
            .collect(Collectors.toList());
    }

    private synchronized void loadByKey(KEY key) {
        List<ENTITY> entities = getByKey(key);
        log.info("Entities {} loaded by key {}: {}", entityName, key, entities.size());
        addToCache(key, entities);
    }

    private void addToCache(KEY key, List<ENTITY> entities) {
        log.debug("Adding to cache entities with key {}: {}", key, entities);
        List<ENTITY> filteredList = filterDeleted(entities);
        Map<ID, ENTITY> map = mapEntities(filteredList);
        ExpirableEntity<ConcurrentHashMap<ID, ModifiableEntity<ENTITY>>> wrappedEntities = wrapEntities(map);
        cacheMap.put(key, wrappedEntities);
    }

    private List<ENTITY> filterDeleted(List<ENTITY> entities) {
        return entities.stream()
            .filter(entity -> !deleteQueue.contains(entity.getId()))
            .collect(Collectors.toList());
    }

    private Map<ID, ENTITY> mapEntities(List<ENTITY> values) {
        return values.stream()
            .collect(Collectors.toMap(Persistable::getId, Function.identity()));
    }

    private ExpirableEntity<ConcurrentHashMap<ID, ModifiableEntity<ENTITY>>> wrapEntities(Map<ID, ENTITY> map) {
        Map<ID, ModifiableEntity<ENTITY>> entities = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, identityEntry -> new ModifiableEntity<>(identityEntry.getValue(), false)));
        return new ExpirableEntity<>(new ConcurrentHashMap<>(entities), cacheContext);
    }

    @Override
    public Iterable<ENTITY> findAllById(Iterable<ID> iterable) {
        List<ID> ids = CollectionUtil.toList(iterable);
        return CollectionUtil.toList(findAll()).stream()
            .filter(entity -> ids.contains(entity.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(ID id) {
        log.debug("Deleting entity {} with id {}", entityName, id);
        deleteQueue.add(id);
        removeFromCache(id);
    }

    private void removeFromCache(ID id) {
        cacheMap.values().forEach(map -> map.getEntity().remove(id));
    }

    @Override
    public void delete(ENTITY entity) {
        deleteById(Objects.requireNonNull(entity.getId()));
    }

    @Override
    public void deleteAll(Iterable<? extends ENTITY> iterable) {
        List<ID> ids = CollectionUtil.toList(iterable)
            .stream()
            .map(Persistable::getId)
            .collect(Collectors.toList());

        log.debug("Deleting entities {} with id {}", entityName, ids);

        cacheMap.values()
            .forEach(expirableMap -> {
                boolean elementWasRemoved = expirableMap.getEntity().entrySet().removeIf(entry -> ids.contains(entry.getKey()));
                if (elementWasRemoved) {
                    expirableMap.updateLastAccess();
                }
            });
        deleteQueue.addAll(ids);
    }

    @Override
    public void deleteAll() {
        log.warn("Deleting all entities: {}", entityName);
        cacheMap.clear();
        deleteQueue.clear();
        repository.deleteAll();
    }
}
