package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.CacheComponentWrapper;
import com.github.saphyra.skyxplore.app.common.utils.CollectionUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
//TODO refactor
public abstract class CacheRepository<KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> implements CrudRepository<ENTITY, ID> {
    private final CacheMap<KEY, ID, ENTITY> cacheMap = new CacheMap<>();
    private final Set<ID> deleteQueue = ConcurrentHashMap.newKeySet();

    private final String entityName;
    protected final REPOSITORY repository;
    private final Function<ENTITY, KEY> keyMapper;
    private final CacheComponentWrapper cacheComponentWrapper;
    private final CacheContext cacheContext;

    protected CacheRepository(REPOSITORY repository, Function<ENTITY, KEY> keyMapper, CacheContext cacheContext) {
        this.repository = repository;
        this.keyMapper = keyMapper;
        this.cacheContext = cacheContext;
        this.entityName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        cacheComponentWrapper = cacheContext.getCacheComponentWrapper();
    }

    public abstract List<ENTITY> getByKey(KEY key);

    public abstract void deleteByIds(List<ID> ids);

    //TODO unit test
    protected void deleteByKey(KEY key) {
        cacheComponentWrapper.getDeleteByKey().deleteByKey(this, key);
    }

    //TODO unit test
    protected Map<ID, ENTITY> getMapByKey(KEY key) {
        return cacheComponentWrapper.getGetMapByKey().getMapByKey(this, key, cacheContext);
    }

    //TODO unit test
    public void fullSync() {
        log.info("Executing full-sync for entity {}...", entityName);
        processDeletions();
        syncChanges();
        evictExpiredEntities();
    }

    //TODO unit test
    public void evictExpiredEntities() {
        synchronized (cacheMap) {
            cacheComponentWrapper.getEvictExpiredEntitiesComponent().evictExpiredEntities(this);
        }
    }

    //TODO unit test
    public void syncChanges() {
        cacheComponentWrapper.getSyncChangesComponent().syncChanges(this);
    }

    //TODO unit test
    public void processDeletions() {
        synchronized (deleteQueue) {
            cacheComponentWrapper.getProcessDeletionsComponent().processDeletions(this);
        }
    }

    @Override
    //TODO unit test
    public <S extends ENTITY> S save(S entity) {
        return cacheComponentWrapper.getSaveComponent().save(this, keyMapper, entity, cacheContext);
    }

    @Override
    //TODO unit test
    public <S extends ENTITY> Iterable<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(this::save);
        return iterable;
    }

    @Override
    public Optional<ENTITY> findById(ID id) {
        return getCacheComponentWrapper().getFindById().findById(this, keyMapper, id, cacheContext);
    }

    @Override
    //TODO unit test
    public boolean existsById(ID id) {
        if (deleteQueue.contains(id)) {
            log.debug("Entity {} with id {} is deleted.", entityName, id);
            return false;
        }
        return repository.existsById(id);
    }

    @Override
    //TODO unit test
    public Iterable<ENTITY> findAll() {
        return cacheComponentWrapper.getFindAll().findAll(this, keyMapper, cacheContext);
    }

    @Override
    //TODO unit test
    public Iterable<ENTITY> findAllById(Iterable<ID> iterable) {
        List<ID> ids = CollectionUtil.toList(iterable);
        return CollectionUtil.toList(findAll()).stream()
            .filter(entity -> ids.contains(entity.getId()))
            .collect(Collectors.toList());
    }

    @Override
    //TODO unit test
    public long count() {
        return repository.count();
    }

    @Override
    //TODO unit test
    public void deleteById(ID id) {
        log.debug("Deleting entity {} with id {}", entityName, id);
        deleteQueue.add(id);
        removeFromCache(id);
    }

    private void removeFromCache(ID id) {
        cacheMap.values().forEach(map -> map.getEntity().remove(id));
    }

    @Override
    //TODO unit test
    public void delete(ENTITY entity) {
        deleteById(Objects.requireNonNull(entity.getId()));
    }

    @Override
    //TODO unit test
    public void deleteAll(Iterable<? extends ENTITY> entities) {
        cacheComponentWrapper.getDeleteAll().deleteAll(this, entities);
    }

    @Override
    //TODO unit test
    public void deleteAll() {
        log.warn("Deleting all entities: {}", entityName);
        cacheMap.clear();
        deleteQueue.clear();
        repository.deleteAll();
    }
}
