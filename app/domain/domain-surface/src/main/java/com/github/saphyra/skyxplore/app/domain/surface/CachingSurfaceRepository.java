package com.github.saphyra.skyxplore.app.domain.surface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingSurfaceRepository extends CacheRepository<String, SurfaceEntity, String, SurfaceRepository> implements SurfaceRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    public CachingSurfaceRepository(SurfaceRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter, CacheContext cacheContext) {
        super(repository, SurfaceEntity::getGameId, cacheContext);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    public List<SurfaceEntity> getByKey(String gameId) {
        List<SurfaceEntity> entities = repository.getByGameId(gameId);
        log.info("SurfaceEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    public void deleteByIds(List<String> surfaceIds) {
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
