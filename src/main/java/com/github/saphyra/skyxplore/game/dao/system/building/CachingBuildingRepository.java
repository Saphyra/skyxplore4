package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
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
public class CachingBuildingRepository extends CacheRepository<String, BuildingEntity, String, BuildingRepository> implements BuildingRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    public CachingBuildingRepository(BuildingRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter, CacheContext cacheContext) {
        super(repository, BuildingEntity::getGameId, cacheContext);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<BuildingEntity> getByKey(String gameId) {
        List<BuildingEntity> entities = repository.getByGameId(gameId);
        log.info("BuildingEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> buildingIds) {
        repository.deleteByBuildingIdIn(buildingIds);
    }

    @Override
    public void deleteByBuildingIdIn(List<String> buildingIds) {
        buildingIds.forEach(this::deleteById);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public Optional<BuildingEntity> findByBuildingIdAndPlayerId(String buildingId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(buildingEntity -> buildingEntity.getBuildingId().equals(buildingId))
            .filter(buildingEntity -> buildingEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public Optional<BuildingEntity> findBySurfaceIdAndPlayerId(String surfaceId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(buildingEntity -> buildingEntity.getSurfaceId().equals(surfaceId))
            .filter(buildingEntity -> buildingEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public List<BuildingEntity> getByStarIdAndBuildingDataIdAndPlayerId(String starId, String dataId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(buildingEntity -> buildingEntity.getStarId().equals(starId))
            .filter(buildingEntity -> buildingEntity.getBuildingDataId().equals(dataId))
            .filter(buildingEntity -> buildingEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());

    }

    @Override
    public List<BuildingEntity> getByStarIdAndPlayerId(String starId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(buildingEntity -> buildingEntity.getStarId().equals(starId))
            .filter(buildingEntity -> buildingEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }

    @Override
    public List<BuildingEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }
}
