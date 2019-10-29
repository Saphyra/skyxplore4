package com.github.saphyra.skyxplore.game.game.service.creation;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.GameDao;
import com.github.saphyra.skyxplore.game.map.star.creation.StarCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameCreationService {
    private final ApplicationEventPublisher eventPublisher;
    private final GameDao gameDao;
    private final GameFactroy gameFactroy;
    private final StarCreationService starCreationService;

    public String createGame(UUID userId, String gameName) {
        StopWatch stopWatch = new StopWatch("GameCreation");
        stopWatch.start();
        log.debug("GameName: {}", gameName);
        Game game = gameFactroy.create(userId, gameName);
        gameDao.save(game);
        starCreationService.createStars(userId, game.getGameId());
        String gameId = game.getGameId().toString();
        stopWatch.stop();
        log.info("Game created with gameId {} in {} seconds", gameId, stopWatch.getTotalTimeSeconds());
        return gameId;
    }
}
