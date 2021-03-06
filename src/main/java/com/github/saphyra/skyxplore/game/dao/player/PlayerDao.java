package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class PlayerDao extends AbstractDao<PlayerEntity, Player, String, PlayerRepository> {
    private final UuidConverter uuidConverter;

    PlayerDao(PlayerConverter converter, PlayerRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting players for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Player> getByGameId(UUID gameId) {
        return converter.convertEntity(repository.getByGameId(
            uuidConverter.convertDomain(gameId)
        ));
    }

    @Override
    public void saveAll(List<Player> players) {
        repository.saveAll(converter.convertDomain(players));
    }

    Optional<Player> findPlayerByGameIdAndPlayerId(UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.findByGameIdAndPlayerId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
