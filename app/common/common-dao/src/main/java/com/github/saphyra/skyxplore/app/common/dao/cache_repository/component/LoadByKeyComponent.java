package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class LoadByKeyComponent {
    private final AddToCacheComponent addToCache;

    synchronized <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void loadByKey(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, KEY key, CacheContext cacheContext) {
        List<ENTITY> entities = repository.getByKey(key);
        log.info("Entities {} loaded by key {}: {}", repository.getEntityName(), key, entities.size());
        addToCache.addToCache(repository, key, entities, cacheContext);
    }
}
