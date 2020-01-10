package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingStarRepository extends CacheRepository<String, StarEntity, String, StarRepository> implements StarRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    protected CachingStarRepository(StarRepository repository, UuidConverter uuidConverter, RequestContextHolder requestContextHolder) {
        super(repository, StarEntity::getGameId);
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
    protected void deleteById(List<String> starIds) {
        repository.deleteByStarIdIn(starIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        cacheMap.remove(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public void deleteByStarIdIn(List<String> starIds) {
        repository.deleteByStarIdIn(starIds);
    }

    @Override
    public List<StarEntity> getByOwnerId(String ownerId) {
        return getMap(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<StarEntity> findByStarIdAndOwnerId(String starId, String ownerId) {
        return getMap(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getStarId().equals(starId))
            .filter(constructionEntity -> constructionEntity.getOwnerId().equals(ownerId))
            .findAny();
    }

    @Override
    public List<StarEntity> getByGameId(String gameId) {
        //noinspection SimplifyStreamApiCallChains
        return Optional.ofNullable(cacheMap.get(gameId))
            .map(map -> map.values().stream().collect(Collectors.toList()))
            .orElseGet(() -> addToCache(gameId, getByKey(gameId)));
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
