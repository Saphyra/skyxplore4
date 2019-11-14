package com.github.saphyra.skyxplore.game.rest.controller.menu;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.module.game.GameViewConverter;
import com.github.saphyra.skyxplore.game.dao.game.GameDao;
import com.github.saphyra.skyxplore.game.module.game.service.creation.GameCreationService;
import com.github.saphyra.skyxplore.game.module.game.service.deletion.GameDeletionService;
import com.github.saphyra.skyxplore.game.rest.view.GameView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GameController {
    private static final String CREATE_GAME_MAPPING = API_PREFIX + "/game";
    private static final String DELETE_GAME_MAPPING = API_PREFIX + "/game/{gameId}";
    private static final String GET_GAMES_MAPPING = API_PREFIX + "/game";

    private final GameCreationService gameCreationService;
    private final GameDao gameDao;
    private final GameDeletionService gameDeletionService;
    private final GameViewConverter gameViewConverter;

    @PutMapping(CREATE_GAME_MAPPING)
    @Transactional
    public String createGame(
            @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
            @RequestBody OneStringParamRequest gameName
    ) {
        log.info("{} wants to create a game.", userId);
        return gameCreationService.createGame(userId, gameName.getValue());
    }

    @DeleteMapping(DELETE_GAME_MAPPING)
    @Transactional
    public void deleteGame(
            @PathVariable("gameId") UUID gameId,
            @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId
    ) {
        log.info("{} wants to delete game {}", userId, gameId);
        gameDeletionService.deleteByGameIdAndUserId(gameId, userId);
    }

    @GetMapping(GET_GAMES_MAPPING)
    List<GameView> getGames(@CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId) {
        log.info("{} wants to know his games", userId);
        return gameViewConverter.convertDomain(gameDao.getByUserId(userId));
    }
}
