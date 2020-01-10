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
public class CachingResearchRepository extends CacheRepository<String, ResearchEntity, String, ResearchRepository> implements ResearchRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    protected CachingResearchRepository(ResearchRepository repository, UuidConverter uuidConverter, RequestContextHolder requestContextHolder) {
        super(repository, ResearchEntity::getGameId);
        this.uuidConverter = uuidConverter;
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    protected List<ResearchEntity> getByKey(String gameId) {
        List<ResearchEntity> entities = repository.getByGameId(gameId);
        log.info("StarConnectionEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteById(List<String> researchIds) {
        repository.deleteByResearchIdIn(researchIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        cacheMap.remove(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public void deleteByResearchIdIn(List<String> researchIds) {
        repository.deleteByResearchIdIn(researchIds);
    }

    @Override
    public List<ResearchEntity> getByGameId(String gameId) {
        //noinspection SimplifyStreamApiCallChains
        return Optional.ofNullable(cacheMap.get(gameId))
            .map(map -> map.values().stream().collect(Collectors.toList()))
            .orElseGet(() -> addToCache(gameId, getByKey(gameId)));
    }

    @Override
    public List<ResearchEntity> getByStarIdAndPlayerId(String starId, String playerId) {
        return getMap(getGameId()).values()
            .stream()
            .parallel()
            .filter(constructionEntity -> constructionEntity.getStarId().equals(starId))
            .filter(constructionEntity -> constructionEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
