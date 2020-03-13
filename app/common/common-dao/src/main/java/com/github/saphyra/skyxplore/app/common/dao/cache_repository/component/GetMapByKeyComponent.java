package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetMapByKeyComponent {
    private final ExtractEntitiesComponent extractEntities;
    private final GetEntityMappingComponent getEntityMapping;

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> Map<ID, ENTITY> getMapByKey(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, KEY key, CacheContext cacheContext) {
        log.debug("Querying {}(s) by key {}", repository.getEntityName(), key);
        EntityMapping<ID, ENTITY> entityMapping = getEntityMapping.getEntityMapping(repository, key, cacheContext);
        return extractEntities.extractEntities(entityMapping);
    }
}
