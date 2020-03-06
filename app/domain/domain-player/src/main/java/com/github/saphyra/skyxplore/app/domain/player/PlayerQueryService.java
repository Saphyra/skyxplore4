package com.github.saphyra.skyxplore.app.domain.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerQueryService {
    private final PlayerDao playerDao;
    private final RequestContextHolder requestContextHolder;

    public Optional<Player> findByGameIdAndPlayerId() {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return playerDao.findPlayerByGameIdAndPlayerId(gameId, playerId);
    }

    public UUID findPlayerIdByUserIdAndGameId(UUID gameId) {
        RequestContext context = requestContextHolder.get();
        UUID userId = context.getUserId();
        return playerDao.getByGameId(gameId).stream()
            .filter(player -> !player.isAi())
            .map(Player::getPlayerId)
            .findFirst()
            .orElseThrow(() -> ExceptionFactory.playerNotFound(userId, gameId));
    }

    public List<Player> getByGameId() {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        return playerDao.getByGameId(gameId);
    }
}
