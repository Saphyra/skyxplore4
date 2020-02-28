package com.github.saphyra.skyxplore.app.domain.game.domain;

import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//TODO unit test
public class GameQueryService {
    private final GameDao gameDao;
    private final RequestContextHolder requestContextHolder;

    public Game findByGameIdAndUserIdValidated() {
        RequestContext requestContext = requestContextHolder.get();
        UUID gameId = requestContext.getGameId();
        return findByGameIdAndUserId(gameId)
            .orElseThrow(() -> ExceptionFactory.gameNotFound(gameId, requestContext.getUserId()));
    }

    public Optional<Game> findByGameIdAndUserId(UUID gameId) {
        RequestContext requestContext = requestContextHolder.get();
        UUID userId = requestContext.getUserId();
        return gameDao.findByGameIdAndUserId(gameId, userId);
    }

    public List<Game> getByUserId() {
        return gameDao.getByUserId(requestContextHolder.get().getUserId());
    }
}
