package com.github.saphyra.skyxplore.game.player;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.game.player.domain.Player;
import com.github.saphyra.skyxplore.game.player.domain.PlayerDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerQueryService {
    private final PlayerDao playerDao;

    public UUID findPlayerIdByUserIdAndGameId(UUID userId, UUID gameId) {
        return playerDao.getByUserIdAndGameId(userId, gameId).stream()
            .filter(player -> !player.isAi())
            .map(Player::getPlayerId)
            .findFirst()
            .orElseThrow(() -> ExceptionFactory.playerNotFound(userId, gameId));
    }
}
