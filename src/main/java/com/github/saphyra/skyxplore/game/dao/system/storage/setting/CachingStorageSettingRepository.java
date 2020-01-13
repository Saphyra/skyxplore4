package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

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
public class CachingStorageSettingRepository extends CacheRepository<String, StorageSettingEntity, String, StorageSettingRepository> implements StorageSettingRepository {
    protected CachingStorageSettingRepository(StorageSettingRepository repository, CacheContext cacheContext) {
        super(repository, StorageSettingEntity::getGameId, cacheContext);
    }

    @Override
    protected List<StorageSettingEntity> getByKey(String gameId) {
        List<StorageSettingEntity> entities = repository.getByGameId(gameId);
        log.info("StorageSettingEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByStorageSettingIdIn(ids);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public void deleteByStorageSettingIdIn(List<String> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public List<StorageSettingEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }
}
