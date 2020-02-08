package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
