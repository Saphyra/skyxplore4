package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingSurfaceRepository extends CacheRepository<String, SurfaceEntity, String, SurfaceRepository> implements SurfaceRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    public CachingSurfaceRepository(SurfaceRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter, CacheContext cacheContext) {
        super(repository, SurfaceEntity::getGameId, cacheContext);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<SurfaceEntity> getByKey(String gameId) {
        List<SurfaceEntity> entities = repository.getByGameId(gameId);
        log.info("SurfaceEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> surfaceIds) {
        repository.deleteBySurfaceIdIn(surfaceIds);
    }

    @Override
    public void deleteBySurfaceIdIn(List<String> surfaceIds) {
        surfaceIds.forEach(this::deleteById);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public Optional<SurfaceEntity> findBySurfaceIdAndPlayerId(String surfaceId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(surfaceEntity -> surfaceEntity.getSurfaceId().equals(surfaceId))
            .filter(surfaceEntity -> surfaceEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public List<SurfaceEntity> getByStarIdAndPlayerId(String starId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(surfaceEntity -> surfaceEntity.getStarId().equals(starId))
            .filter(surfaceEntity -> surfaceEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }

    @Override
    public List<SurfaceEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }
}
