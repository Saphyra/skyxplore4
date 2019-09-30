package com.github.saphyra.skyxplore.game.player.domain;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;

@Component
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
}
