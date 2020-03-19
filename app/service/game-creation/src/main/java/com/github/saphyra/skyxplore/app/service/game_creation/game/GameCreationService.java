package com.github.saphyra.skyxplore.app.service.game_creation.game;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.github.saphyra.skyxplore.app.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.game.Game;
import com.github.saphyra.skyxplore.app.domain.game.GameCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameCreationService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final DomainSaverService domainSaverService;
    private final GameCommandService gameCommandService;
    private final GameFactory gameFactory;
    private final GameNameValidator gameNameValidator;
    private final RequestContextHolder requestContextHolder;

    @Transactional
    public UUID createGame(String gameName) {
        gameNameValidator.validate(gameName);

        UUID userId = requestContextHolder.get()
            .getUserId();
        StopWatch stopWatch = new StopWatch("GameCreation");
        stopWatch.start();
        log.debug("GameName: {}", gameName);
        Game game = gameFactory.create(userId, gameName);
        gameCommandService.save(game);
        applicationEventPublisher.publishEvent(new GameCreatedEvent(game.getGameId()));
        UUID gameId = game.getGameId();
        domainSaverService.save();
        stopWatch.stop();
        log.info("Game created with gameId {} in {} seconds", gameId, stopWatch.getTotalTimeSeconds());
        return gameId;
    }
}
