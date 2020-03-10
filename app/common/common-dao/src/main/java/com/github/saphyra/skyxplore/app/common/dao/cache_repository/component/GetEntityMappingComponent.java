package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheMap;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class GetEntityMappingComponent {
    private final LoadByKeyComponent loadByKey;

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> EntityMapping<ID, ENTITY> getEntityMapping(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, KEY key, CacheContext cacheContext){
        CacheMap<KEY, ID, ENTITY> cacheMap = repository.getCacheMap();
        if (!cacheMap.containsKey(key)) {
            log.debug("Cache does not contain key {}. Loading entities...", key);
            loadByKey.loadByKey(repository, key, cacheContext);
        }
        return cacheMap.get(key).updateLastAccessAndGetEntity();
    }
}
