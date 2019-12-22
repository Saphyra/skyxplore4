package com.github.saphyra.skyxplore.game.dao.game;

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
public class GameQueryService {
    private final GameDao gameDao;
    private final RequestContextHolder requestContextHolder;

    public Optional<Game> findByGameIdAndUserId(UUID gameId) {
        return gameDao.findByGameIdAndUserId(gameId, requestContextHolder.get().getUserId());
    }

    public Game findByGameIdAndUserIdValidated(UUID gameId) {
        RequestContext requestContext = requestContextHolder.get();
        UUID userId = requestContext.getUserId();
        return gameDao.findByGameIdAndUserId(gameId, userId)
                .orElseThrow(() -> ExceptionFactory.gameNotFound(gameId, userId));
    }

    public List<Game> getByUserId() {
        return gameDao.getByUserId(requestContextHolder.get().getUserId());
    }
}
