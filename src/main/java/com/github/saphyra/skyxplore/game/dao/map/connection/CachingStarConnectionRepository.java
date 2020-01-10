package com.github.saphyra.skyxplore.game.dao.map.connection;

import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@Slf4j
public class CachingStarConnectionRepository extends CacheRepository<String, StarConnectionEntity, String, StarConnectionRepository> implements StarConnectionRepository {

    protected CachingStarConnectionRepository(StarConnectionRepository repository, CacheContext cacheContext) {
        super(repository, StarConnectionEntity::getGameId, cacheContext);
    }

    @Override
    protected List<StarConnectionEntity> getByKey(String gameId) {
        List<StarConnectionEntity> entities = repository.getByGameId(gameId);
        log.info("StarConnectionEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> connectionIds) {
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
