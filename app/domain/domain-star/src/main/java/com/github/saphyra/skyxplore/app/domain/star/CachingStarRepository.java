package com.github.saphyra.skyxplore.app.domain.star;

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
//TODO unit test
public class CachingStarRepository extends CacheRepository<String, StarEntity, String, StarRepository> implements StarRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    protected CachingStarRepository(StarRepository repository, UuidConverter uuidConverter, RequestContextHolder requestContextHolder, CacheContext cacheContext) {
        super(repository, StarEntity::getGameId, cacheContext);
        this.uuidConverter = uuidConverter;
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    public List<StarEntity> getByKey(String gameId) {
        List<StarEntity> entities = repository.getByGameId(gameId);
        log.info("StarEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    public void deleteByIds(List<String> starIds) {
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
