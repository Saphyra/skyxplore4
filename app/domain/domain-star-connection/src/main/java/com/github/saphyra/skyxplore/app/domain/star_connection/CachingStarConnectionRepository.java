package com.github.saphyra.skyxplore.app.domain.star_connection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingStarConnectionRepository extends CacheRepository<String, StarConnectionEntity, String, StarConnectionRepository> implements StarConnectionRepository {

    protected CachingStarConnectionRepository(StarConnectionRepository repository, CacheContext cacheContext) {
        super(repository, StarConnectionEntity::getGameId, cacheContext);
    }

    @Override
    public List<StarConnectionEntity> getByKey(String gameId) {
        List<StarConnectionEntity> entities = repository.getByGameId(gameId);
        log.info("StarConnectionEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    public void deleteByIds(List<String> connectionIds) {
        repository.deleteByConnectionIdIn(connectionIds);
    }

    @Override
    public void deleteByConnectionIdIn(List<String> connectionIds) {
        connectionIds.forEach(this::deleteById);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public List<StarConnectionEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }
}
