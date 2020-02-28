package com.github.saphyra.skyxplore.app.domain.game.service;

import com.github.saphyra.skyxplore.app.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.ExecutorServiceBean;
import com.github.saphyra.skyxplore.app.domain.game.domain.Game;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class GameDeletionService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final List<CommandService> deletables;
    private final ExecutorServiceBean executorServiceBean;
    private final GameQueryService gameQueryService;
    private final RequestContextHolder requestContextHolder;

    @Transactional
    public void deleteGame(UUID gameId) {
        Optional<Game> gameOptional = gameQueryService.findByGameIdAndUserId(gameId);
        if (gameOptional.isPresent()) {
            executorServiceBean.execute(() -> {
                    deletables.stream()
                        .parallel()
                        .forEach(deletableByGameId -> deletableByGameId.deleteByGameId(gameId));
                    log.info("Game with id {} is deleted.", gameId);
                    applicationEventPublisher.publishEvent(new GameDeletedEvent());
                }
            );
        } else {
            throw ExceptionFactory.gameNotFound(gameId, requestContextHolder.get().getUserId());
        }
    }
}
