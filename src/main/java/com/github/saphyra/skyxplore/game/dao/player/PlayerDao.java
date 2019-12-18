package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
//TODO make package-private
public class PlayerDao extends AbstractDao<PlayerEntity, Player, String, PlayerRepository> {
    private final UuidConverter uuidConverter;

    public PlayerDao(PlayerConverter converter, PlayerRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void gameDeletedEventListener(UUID gameId, UUID userId){
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    public List<Player> getByUserIdAndGameId(UUID userId, UUID gameId) {
        return converter.convertEntity(repository.getByUserIdAndGameId(
            uuidConverter.convertDomain(userId),
            uuidConverter.convertDomain(gameId)
        ));
    }
}
