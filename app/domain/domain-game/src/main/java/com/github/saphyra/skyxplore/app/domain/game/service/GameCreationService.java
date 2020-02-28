package com.github.saphyra.skyxplore.app.domain.game.service;

import com.github.saphyra.skyxplore.app.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.game.domain.Game;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameCreationService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final DomainSaverService domainSaverService;
    private final GameCommandService gameCommandService;
    private final GameFactory gameFactory;
    private final RequestContextHolder requestContextHolder;

    @Transactional
    public String createGame(String gameName) {
        UUID userId = requestContextHolder.get()
            .getUserId();
        StopWatch stopWatch = new StopWatch("GameCreation");
        stopWatch.start();
        log.debug("GameName: {}", gameName);
        Game game = gameFactory.create(userId, gameName);
        gameCommandService.save(game);
        applicationEventPublisher.publishEvent(new GameCreatedEvent(game.getGameId()));
        String gameId = game.getGameId().toString();
        domainSaverService.save();
        stopWatch.stop();
        log.info("Game created with gameId {} in {} seconds", gameId, stopWatch.getTotalTimeSeconds());
        return gameId;
    }
}
