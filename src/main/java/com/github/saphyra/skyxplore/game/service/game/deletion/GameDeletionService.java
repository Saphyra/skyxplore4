package com.github.saphyra.skyxplore.game.service.game.deletion;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.skyxplore.game.dao.game.GameCommandService;
import com.github.saphyra.skyxplore.game.dao.game.GameQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameDeletionService {
    private final ApplicationEventPublisher eventPublisher;
    private final GameCommandService gameCommandService;
    private final GameQueryService gameQueryService;

    public void deleteByGameIdAndUserId(UUID gameId) {
        deleteGame(gameQueryService.findByGameIdAndUserIdValidated(gameId));
    }

    private void deleteGame(Game game) {
        gameCommandService.delete(game);
        eventPublisher.publishEvent(new GameDeletedEvent(game.getUserId(), game.getGameId()));
        log.info("Game with id {} is deleted.", game.getGameId());
    }
}
