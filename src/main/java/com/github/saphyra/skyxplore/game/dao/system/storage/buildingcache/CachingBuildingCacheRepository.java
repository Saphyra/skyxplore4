package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingBuildingCacheRepository extends CacheRepository<String, BuildingCacheEntity, String, BuildingCacheRepository> implements BuildingCacheRepository {
    protected CachingBuildingCacheRepository(BuildingCacheRepository repository, CacheContext cacheContext) {
        super(repository, BuildingCacheEntity::getDataId, cacheContext);
    }

    @Override
    protected List<BuildingCacheEntity> getByKey(String gameId) {
        List<BuildingCacheEntity> entities = repository.getByGameId(gameId);
        log.info("BuildingCacheEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByBuildingCacheIdIn(ids);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public void deleteByBuildingCacheIdIn(List<String> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public List<BuildingCacheEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }
}
