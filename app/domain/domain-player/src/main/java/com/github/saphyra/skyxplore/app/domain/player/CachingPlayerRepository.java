package com.github.saphyra.skyxplore.app.domain.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class CachingPlayerRepository extends CacheRepository<String, PlayerEntity, String, PlayerRepository> implements PlayerRepository {
    protected CachingPlayerRepository(PlayerRepository repository, CacheContext cacheContext) {
        super(repository, PlayerEntity::getGameId, cacheContext);
    }

    @Override
    public List<PlayerEntity> getByKey(String gameId) {
        List<PlayerEntity> entities = repository.getByGameId(gameId);
        log.info("PlayerEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    public void deleteByIds(List<String> playerIds) {
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
