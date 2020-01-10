package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingPlayerRepository extends CacheRepository<String, PlayerEntity, String, PlayerRepository> implements PlayerRepository {
    protected CachingPlayerRepository(PlayerRepository repository, Function<PlayerEntity, String> keyMapper) {
        super(repository, keyMapper);
    }

    @Override
    protected List<PlayerEntity> getByKey(String gameId) {
        List<PlayerEntity> entities = repository.getByGameId(gameId);
        log.info("PlayerEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> playerIds) {
        repository.deleteByPlayerIdIn(playerIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        cacheMap.remove(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public void deleteByPlayerIdIn(List<String> playerIds) {
        playerIds.forEach(this::deleteById);
    }

    @Override
    public List<PlayerEntity> getByGameId(String gameId) {
        //noinspection SimplifyStreamApiCallChains
        return Optional.ofNullable(cacheMap.get(gameId))
            .map(map -> map.values().stream().collect(Collectors.toList()))
            .orElseGet(() -> addToCache(gameId, getByKey(gameId)));
    }

    @Override
    public Optional<PlayerEntity> findByGameIdAndPlayerId(String gameId, String playerId) {
        return Optional.ofNullable(getMap(gameId).get(playerId));
    }
}
