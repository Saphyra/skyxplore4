package com.github.saphyra.skyxplore.game.dao.common.cache;

import com.github.saphyra.skyxplore.common.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class CacheRepository<KEY, ENTITY extends Persistable<ID>, ID> implements CrudRepository<ENTITY, ID> {
    protected final ConcurrentHashMap<KEY, ConcurrentHashMap<ID, ENTITY>> cacheMap = new ConcurrentHashMap<>();
    protected final Set<ID> deleteQueue = ConcurrentHashMap.newKeySet();

    protected final CrudRepository<ENTITY, ID> repository;
    protected final Function<ENTITY, KEY> keyMapper;

    protected abstract List<ENTITY> getByKey(KEY key);

    private ConcurrentHashMap<ID, ENTITY> getMap(KEY key) {
        if (cacheMap.containsKey(key)) {
            loadByKey(key);
        }
        return cacheMap.get(key);
    }

    private void loadByKey(KEY key) {
        List<ENTITY> entities = getByKey(key);
        addToCache(key, entities);
    }

    private void addToCache(KEY key, List<ENTITY> entities) {
        Map<ID, ENTITY> map = mapEntities(filterDeleted(entities));
        cacheMap.put(key, new ConcurrentHashMap<>(map));
    }

    private Map<ID, ENTITY> mapEntities(List<ENTITY> values) {
        return values.stream().collect(Collectors.toMap(Persistable::getId, Function.identity()));
    }

    private List<ENTITY> filterDeleted(List<ENTITY> entities) {
        return entities.stream()
            .filter(entity -> !deleteQueue.contains(entity.getId()))
            .collect(Collectors.toList());
    }

    public void processDeletions() {
        repository.deleteAll(repository.findAllById(deleteQueue));
        deleteQueue.clear();
    }

    @Override
    public <S extends ENTITY> S save(S s) {
        getMap(keyMapper.apply(s)).put(Objects.requireNonNull(s.getId()), s);
        deleteQueue.remove(s.getId());
        return s;
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
            .flatMap(map -> map.values().stream())
            .filter(entity -> Objects.equals(entity.getId(), id))
            .findAny();
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
        cacheMap.values().forEach(map -> map.remove(id));
    }

    @Override
    public void delete(ENTITY entity) {
        deleteById(Objects.requireNonNull(entity.getId()));
    }

    @Override
    public void deleteAll(Iterable<? extends ENTITY> iterable) {
        iterable.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        deleteAll(findAll());
    }
}
