package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class PlayerDao extends AbstractDao<PlayerEntity, Player, String, PlayerRepository> implements DeletableByGameId {
    private final UuidConverter uuidConverter;

    PlayerDao(PlayerConverter converter, PlayerRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting players for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Player> getByUserIdAndGameId(UUID userId, UUID gameId) {
        return converter.convertEntity(repository.getByUserIdAndGameId(
            uuidConverter.convertDomain(userId),
            uuidConverter.convertDomain(gameId)
        ));
    }
}
