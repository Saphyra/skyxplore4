package com.github.saphyra.skyxplore_deprecated.game.dao.system.construction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingConstructionResourceRequirementRepository extends CacheRepository<String, ConstructionResourceRequirementEntity, String, ConstructionResourceRequirementRepository> implements ConstructionResourceRequirementRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingConstructionResourceRequirementRepository(ConstructionResourceRequirementRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter, CacheContext cacheContext) {
        super(repository, ConstructionResourceRequirementEntity::getGameId, cacheContext);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<ConstructionResourceRequirementEntity> getByKey(String gameId) {
        List<ConstructionResourceRequirementEntity> entities = repository.getByGameId(gameId);
        log.info("BuildingEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByConstructionResourceRequirementIdIn(ids);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public List<ConstructionResourceRequirementEntity> getByConstructionId(String constructionId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(entity -> entity.getConstructionId().equals(constructionId))
            .collect(Collectors.toList());
    }

    @Override
    public List<ConstructionResourceRequirementEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public void deleteByConstructionId(String constructionId) {
        getByConstructionId(constructionId).forEach(this::delete);
    }

    @Override
    public void deleteByConstructionResourceRequirementIdIn(List<String> ids) {
        ids.forEach(this::deleteById);
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
