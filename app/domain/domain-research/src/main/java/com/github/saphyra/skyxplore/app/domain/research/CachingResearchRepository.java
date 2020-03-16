package com.github.saphyra.skyxplore.app.domain.research;

import java.util.ArrayList;
import java.util.List;
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
public class CachingResearchRepository extends CacheRepository<String, ResearchEntity, String, ResearchRepository> implements ResearchRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    protected CachingResearchRepository(ResearchRepository repository, UuidConverter uuidConverter, RequestContextHolder requestContextHolder, CacheContext cacheContext) {
        super(repository, ResearchEntity::getGameId, cacheContext);
        this.uuidConverter = uuidConverter;
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    public List<ResearchEntity> getByKey(String gameId) {
        List<ResearchEntity> entities = repository.getByGameId(gameId);
        log.info("ResearchEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    public void deleteByIds(List<String> researchIds) {
        repository.deleteByResearchIdIn(researchIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public void deleteByResearchIdIn(List<String> researchIds) {
        researchIds.forEach(this::deleteById);
    }

    @Override
    public List<ResearchEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public List<ResearchEntity> getByStarIdAndPlayerId(String starId, String playerId) {
        return getMapByKey(getGameId()).values()
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
