package com.github.saphyra.skyxplore_deprecated.game.service.game.deletion;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.ExecutorServiceBean;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.game.Game;
import com.github.saphyra.skyxplore_deprecated.game.dao.game.GameQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameDeletionService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final List<CommandService> deletables;
    private final ExecutorServiceBean executorServiceBean;
    private final GameQueryService gameQueryService;
    private final RequestContextHolder requestContextHolder;

    public void deleteByGameIdAndUserId(UUID gameId) {
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
