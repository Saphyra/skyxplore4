package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingPlayerRepository extends CacheRepository<String, PlayerEntity, String, PlayerRepository> implements PlayerRepository {
    protected CachingPlayerRepository(PlayerRepository repository, CacheContext cacheContext) {
        super(repository, PlayerEntity::getGameId, cacheContext);
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
        deleteByKey(gameId);
    }

    @Override
    public void deleteByPlayerIdIn(List<String> playerIds) {
        playerIds.forEach(this::deleteById);
    }

    @Override
    public List<PlayerEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public Optional<PlayerEntity> findByGameIdAndPlayerId(String gameId, String playerId) {
        return Optional.ofNullable(getMapByKey(gameId).get(playerId));
    }
}
