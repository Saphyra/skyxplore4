package com.github.saphyra.skyxplore_deprecated.game.dao.map.star;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
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
public class CachingStarRepository extends CacheRepository<String, StarEntity, String, StarRepository> implements StarRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    protected CachingStarRepository(StarRepository repository, UuidConverter uuidConverter, RequestContextHolder requestContextHolder, CacheContext cacheContext) {
        super(repository, StarEntity::getGameId, cacheContext);
        this.uuidConverter = uuidConverter;
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    protected List<StarEntity> getByKey(String gameId) {
        List<StarEntity> entities = repository.getByGameId(gameId);
        log.info("StarEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> starIds) {
        repository.deleteByStarIdIn(starIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public void deleteByStarIdIn(List<String> starIds) {
        starIds.forEach(this::deleteById);
    }

    @Override
    public List<StarEntity> getByOwnerId(String ownerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<StarEntity> findByStarIdAndOwnerId(String starId, String ownerId) {
        return getMapByKey(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getStarId().equals(starId))
            .filter(constructionEntity -> constructionEntity.getOwnerId().equals(ownerId))
            .findAny();
    }

    @Override
    public List<StarEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
