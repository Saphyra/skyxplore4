package com.github.saphyra.skyxplore_deprecated.game.dao.game;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class GameDao extends AbstractDao<GameEntity, Game, String, GameRepository> {
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

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting games with gameId {}", gameId);
        repository.deleteById(uuidConverter.convertDomain(gameId));
    }
}
