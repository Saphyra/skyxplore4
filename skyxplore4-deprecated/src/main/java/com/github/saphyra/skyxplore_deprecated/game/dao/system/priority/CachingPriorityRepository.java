package com.github.saphyra.skyxplore_deprecated.game.dao.system.priority;

import com.github.saphyra.skyxplore.common.utils.UuidConverter;
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
@Slf4j
@Primary
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingPriorityRepository extends CacheRepository<String, PriorityEntity, PriorityEntityId, PriorityRepository> implements PriorityRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingPriorityRepository(PriorityRepository repository, CacheContext cacheContext, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, PriorityEntity::getGameId, cacheContext);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<PriorityEntity> getByKey(String gameId) {
        List<PriorityEntity> entities = repository.getByGameId(gameId);
        log.info("PriorityEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<PriorityEntityId> priorityEntityIds) {
        priorityEntityIds.forEach(repository::deleteById);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public List<PriorityEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public List<PriorityEntity> getByIdStarIdAndPlayerId(String starId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(priorityEntity -> priorityEntity.getId().getStarId().equals(starId))
            .filter(priorityEntity -> priorityEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<PriorityEntity> findByIdAndPlayerId(PriorityEntityId priorityEntityId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(priorityEntity -> priorityEntity.getId().equals(priorityEntityId))
            .filter(priorityEntity -> priorityEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
