package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting;

import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingStorageSettingRepository extends CacheRepository<String, StorageSettingEntity, String, StorageSettingRepository> implements StorageSettingRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingStorageSettingRepository(StorageSettingRepository repository, CacheContext cacheContext, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, StorageSettingEntity::getGameId, cacheContext);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
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

    @Override
    public List<StorageSettingEntity> getByStarIdAndPlayerId(String starId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(entity -> entity.getStarId().equals(starId))
            .filter(entity -> entity.getPlayerId().endsWith(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<StorageSettingEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(entity -> entity.getStarId().equals(starId))
            .filter(entity -> entity.getDataId().equals(dataId))
            .filter(entity -> entity.getPlayerId().endsWith(playerId))
            .findAny();
    }

    @Override
    public Optional<StorageSettingEntity> findByStorageSettingIdAndPlayerId(String storageSettingId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(entity -> entity.getStorageSettingId().equals(storageSettingId))
            .filter(entity -> entity.getPlayerId().endsWith(playerId))
            .findAny();
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
