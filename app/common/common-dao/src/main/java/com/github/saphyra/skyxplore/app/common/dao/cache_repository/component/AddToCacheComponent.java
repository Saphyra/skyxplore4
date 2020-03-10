package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO refactor - split
//TODO unit test
class AddToCacheComponent {
    <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void addToCache(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, KEY key, List<ENTITY> entities, CacheContext cacheContext) {
        log.debug("Adding to cache entities with key {}: {}", key, entities);
        List<ENTITY> filteredList = filterDeleted(repository.getDeleteQueue(), entities);
        Map<ID, ENTITY> map = mapEntities(filteredList);
        ExpirableEntity<EntityMapping<ID, ENTITY>> wrappedEntities = wrapEntities(map, cacheContext);
        repository.getCacheMap().put(key, wrappedEntities);
    }

    private <ID, ENTITY extends SettablePersistable<ID>> List<ENTITY> filterDeleted(Set<ID> deleteQueue, List<ENTITY> entities) {
        return entities.stream()
            .filter(entity -> !deleteQueue.contains(entity.getId()))
            .collect(Collectors.toList());
    }

    private <ID, ENTITY extends SettablePersistable<ID>> Map<ID, ENTITY> mapEntities(List<ENTITY> values) {
        return values.stream()
            .collect(Collectors.toMap(Persistable::getId, Function.identity()));
    }

    private <ID, ENTITY> ExpirableEntity<EntityMapping<ID, ENTITY>> wrapEntities(Map<ID, ENTITY> map, CacheContext cacheContext) {
        Map<ID, ModifiableEntity<ENTITY>> entities = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, identityEntry -> new ModifiableEntity<>(identityEntry.getValue(), false)));
        return new ExpirableEntity<>(new EntityMapping<>(entities), cacheContext);
    }
}
