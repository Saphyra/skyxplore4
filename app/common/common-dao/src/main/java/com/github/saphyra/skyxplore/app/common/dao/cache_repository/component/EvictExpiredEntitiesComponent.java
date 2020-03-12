package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheMap;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EvictExpiredEntitiesComponent {

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void evictExpiredEntities(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository) {
        log.info("Evicting expired entities for entity {}...", repository.getEntityName());
        CacheMap<KEY, ID, ENTITY> cacheMap = repository.getCacheMap();
        List<KEY> expiredKeys = cacheMap.entrySet()
            .stream()
            .filter(expirableEntityEntry -> expirableEntityEntry.getValue().isExpired())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        expiredKeys.forEach(cacheMap::remove);
        log.info("Evicting expired entities finished for entity {}. Number of expired keys: {}", repository.getEntityName(), expiredKeys.size());
    }
}
