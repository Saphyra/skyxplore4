package com.github.saphyra.skyxplore.game.dao.map.connection;

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
public class CachingStarConnectionRepository extends CacheRepository<String, StarConnectionEntity, String, StarConnectionRepository> implements StarConnectionRepository {

    protected CachingStarConnectionRepository(StarConnectionRepository repository) {
        super(repository, StarConnectionEntity::getGameId);
    }

    @Override
    protected List<StarConnectionEntity> getByKey(String gameId) {
        List<StarConnectionEntity> entities = repository.getByGameId(gameId);
        log.info("StarConnectionEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteById(List<String> connectionIds) {
        repository.deleteByConnectionIdIn(connectionIds);
    }

    @Override
    public void deleteByConnectionIdIn(List<String> connectionIds) {
        repository.deleteByConnectionIdIn(connectionIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        cacheMap.remove(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public List<StarConnectionEntity> getByGameId(String gameId) {
        //noinspection SimplifyStreamApiCallChains
        return Optional.ofNullable(cacheMap.get(gameId))
            .map(map -> map.values().stream().collect(Collectors.toList()))
            .orElseGet(() -> addToCache(gameId, getByKey(gameId)));
    }
}
