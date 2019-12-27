package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerQueryService {
    private final PlayerDao playerDao;
    private final RequestContextHolder requestContextHolder;

    public UUID findPlayerIdByUserIdAndGameId(UUID gameId) {
        RequestContext context = requestContextHolder.get();
        UUID userId = context.getUserId();
        return playerDao.getByUserIdAndGameId(gameId).stream()
                .filter(player -> !player.isAi())
                .map(Player::getPlayerId)
                .findFirst()
                .orElseThrow(() -> ExceptionFactory.playerNotFound(userId, gameId));
    }
}
