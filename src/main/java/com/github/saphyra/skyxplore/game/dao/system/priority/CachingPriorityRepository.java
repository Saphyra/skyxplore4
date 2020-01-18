package com.github.saphyra.skyxplore.game.dao.system.priority;

import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Primary
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingPriorityRepository extends CacheRepository<String, PriorityEntity, PriorityEntityId, PriorityRepository> implements PriorityRepository {
    protected CachingPriorityRepository(PriorityRepository repository, CacheContext cacheContext) {
        super(repository, PriorityEntity::getGameId, cacheContext);
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
}
