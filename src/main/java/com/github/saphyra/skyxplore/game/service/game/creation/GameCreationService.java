package com.github.saphyra.skyxplore.game.service.game.creation;

import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.common.DomainSaverService;
import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.skyxplore.game.dao.game.GameCommandService;
import com.github.saphyra.skyxplore.game.service.map.star.creation.StarCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameCreationService {
    private final DomainSaverService domainSaverService;
    private final GameCommandService gameCommandService;
    private final GameFactroy gameFactroy;
    private final RequestContextHolder requestContextHolder;
    private final StarCreationService starCreationService;

    public String createGame(String gameName) {
        UUID userId = requestContextHolder.get()
            .getUserId();
        StopWatch stopWatch = new StopWatch("GameCreation");
        stopWatch.start();
        log.debug("GameName: {}", gameName);
        Game game = gameFactroy.create(userId, gameName);
        gameCommandService.save(game);
        starCreationService.createStars(userId, game.getGameId());
        String gameId = game.getGameId().toString();
        domainSaverService.save();
        stopWatch.stop();
        log.info("Game created with gameId {} in {} seconds", gameId, stopWatch.getTotalTimeSeconds());
        return gameId;
    }
}
