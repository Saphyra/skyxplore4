package com.github.saphyra.skyxplore.game.dao.common.cache;

import com.github.saphyra.skyxplore.common.CollectionUtil;
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
public abstract class CacheRepository<KEY, ENTITY extends Persistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> implements CrudRepository<ENTITY, ID> {
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
        deleteAll(getByKey(key));
    }

    protected Map<ID, ENTITY> getMapByKey(KEY key) {
        return extractEntities(getMap(key));
    }

    private ConcurrentHashMap<ID, ENTITY> extractEntities(ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> map) {
        Map<ID, ENTITY> mapping = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, idModifiableEntityEntry -> idModifiableEntityEntry.getValue().getEntity()));
        return new ConcurrentHashMap<>(mapping);
    }

    private List<ENTITY> filterDeleted(List<ENTITY> entities) {
        return entities.stream()
            .filter(entity -> !deleteQueue.contains(entity.getId()))
            .collect(Collectors.toList());
    }

    public void fullSync() {
        log.info("Executing full-sync for entity {}...", entityName);
        processDeletions();
        syncChanges();
        evictExpiredEntities();
    }

    public void evictExpiredEntities() {
        log.info("Evicting expired entities for entity {}...", entityName);
        cacheMap.entrySet().removeIf(expirableMapEntry -> expirableMapEntry.getValue().isExpired());
        log.info("Evicting expired entities finished for entity {}", entityName);
    }

    public void syncChanges() {
        log.info("Synchronizing modifications for entity {}...", entityName);
        cacheMap.values().forEach(map -> {
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (map) {
                repository.saveAll(filterModified(map.getEntity()));
            }
        });
        log.info("Synchronization finished for entity {}", entityName);
    }

    private List<ENTITY> filterModified(ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> map) {
        return map.values()
            .stream()
            .map(ModifiableEntity::getEntity)
            .collect(Collectors.toList());
    }

    public void processDeletions() {
        log.info("Processing deletions for entity {}...", entityName);
        synchronized (deleteQueue) {
            ArrayList<ID> ids = new ArrayList<>(deleteQueue);
            chunks(ids).forEach(this::deleteByIds);
            deleteQueue.clear();
        }
        log.info("Deletion finished for entity {}", entityName);
    }

    private static <T> List<List<T>> chunks(List<T> bigList) {
        List<List<T>> chunks = new ArrayList<>();

        for (int i = 0; i < bigList.size(); i += CacheRepository.MAX_CHUNK_SIZE) {
            List<T> chunk = bigList.subList(i, Math.min(bigList.size(), i + CacheRepository.MAX_CHUNK_SIZE));
            chunks.add(chunk);
        }

        return chunks;
    }

    @Override
    public <S extends ENTITY> S save(S entity) {
        getMap(keyMapper.apply(entity)).put(Objects.requireNonNull(entity.getId()), new ModifiableEntity<>(entity, true));
        deleteQueue.remove(entity.getId());
        return entity;
    }

    private ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> getMap(KEY key) {
        if (!cacheMap.containsKey(key)) {
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
            return Optional.empty();
        }
        Optional<ENTITY> cachedEntity = cacheMap.values()
            .stream()
            .map(ExpirableEntity::getEntity)
            .flatMap(map -> map.values().stream().map(ModifiableEntity::getEntity))
            .filter(entity -> Objects.equals(entity.getId(), id))
            .findAny();

        cachedEntity.ifPresent(entity -> cacheMap.get(keyMapper.apply(entity)).updateLastAccess());
        return cachedEntity.isPresent() ? cachedEntity : searchInRepository(id);
    }

    private Optional<ENTITY> searchInRepository(ID id) {
        Optional<ENTITY> entityOptional = repository.findById(id);
        entityOptional.ifPresent(entity -> loadByKey(keyMapper.apply(entity)));
        return entityOptional;
    }

    @Override
    public boolean existsById(ID id) {
        if (deleteQueue.contains(id)) {
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
        addToCache(key, entities);
    }

    private void addToCache(KEY key, List<ENTITY> entities) {
        Map<ID, ENTITY> map = mapEntities(filterDeleted(entities));
        ExpirableEntity<ConcurrentHashMap<ID, ModifiableEntity<ENTITY>>> wrappedEntities = wrapEntities(map);
        cacheMap.put(key, wrappedEntities);
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
        deleteAll(findAll());
    }
}
