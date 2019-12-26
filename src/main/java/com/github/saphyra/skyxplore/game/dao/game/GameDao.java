package com.github.saphyra.skyxplore.game.dao.game;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class GameDao extends AbstractDao<GameEntity, Game, String, GameRepository> implements DeletableByGameId {
    private final UuidConverter uuidConverter;

    public GameDao(GameConverter converter, GameRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    List<Game> getByUserId(UUID userId) {
        return converter.convertEntity(repository.getByUserId(uuidConverter.convertDomain(userId)));
    }

    Optional<Game> findByGameIdAndUserId(UUID gameId, UUID userId) {
        return converter.convertEntity(repository.findByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        ));
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting games with gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }
}
