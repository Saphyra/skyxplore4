package com.github.saphyra.skyxplore.app.service.game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.ExecutorServiceBean;
import com.github.saphyra.skyxplore.app.domain.game.Game;
import com.github.saphyra.skyxplore.app.domain.game.GameCommandService;
import com.github.saphyra.skyxplore.app.domain.game.GameQueryService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class GameDeletionService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final List<CommandService<?>> deletables;
    private final ExecutorServiceBean executorServiceBean;
    private final GameCommandService gameCommandService;
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
            gameCommandService.delete(gameOptional.get());
        } else {
            throw ExceptionFactory.gameNotFound(gameId, requestContextHolder.get().getUserId());
        }
    }
}
