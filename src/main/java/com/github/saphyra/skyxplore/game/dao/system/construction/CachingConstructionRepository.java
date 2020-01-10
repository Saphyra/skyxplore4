package com.github.saphyra.skyxplore.game.dao.system.construction;

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
public class CachingConstructionRepository extends CacheRepository<String, ConstructionEntity, String, ConstructionRepository> implements ConstructionRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    public CachingConstructionRepository(ConstructionRepository repository, UuidConverter uuidConverter, RequestContextHolder requestContextHolder, CacheContext cacheContext) {
        super(repository, ConstructionEntity::getGameId, cacheContext);
        this.uuidConverter = uuidConverter;
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    protected List<ConstructionEntity> getByKey(String gameId) {
        List<ConstructionEntity> entities = repository.getByGameId(gameId);
        log.info("ConstructionEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> constructionIds) {
        repository.deleteByConstructionIdIn(constructionIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public Optional<ConstructionEntity> findByConstructionIdAndPlayerId(String constructionId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getConstructionId().equals(constructionId))
            .filter(constructionEntity -> constructionEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public Optional<ConstructionEntity> findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType constructionType, String externalId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getExternalId().equals(externalId))
            .filter(constructionEntity -> constructionEntity.getConstructionType().equals(constructionType))
            .filter(constructionEntity -> constructionEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public Optional<ConstructionEntity> findByConstructionTypeAndSurfaceIdAndPlayerId(ConstructionType constructionType, String surfaceId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getSurfaceId().equals(surfaceId))
            .filter(constructionEntity -> constructionEntity.getConstructionType().equals(constructionType))
            .filter(constructionEntity -> constructionEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public List<ConstructionEntity> getByStarIdAndPlayerId(String starId, String playerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getStarId().equals(starId))
            .filter(constructionEntity -> constructionEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteByConstructionIdIn(List<String> constructionIds) {
        constructionIds.forEach(this::deleteById);
    }

    @Override
    public void deleteByConstructionIdAndPlayerId(String constructionId, String playerId) {
        findByConstructionIdAndPlayerId(constructionId, playerId).ifPresent(this::delete);
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }

    @Override
    public List<ConstructionEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }
}
