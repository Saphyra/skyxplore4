package com.github.saphyra.skyxplore_deprecated.game.rest.controller.menu;

import com.github.saphyra.skyxplore_deprecated.common.OneStringParamRequest;
import com.github.saphyra.skyxplore_deprecated.game.dao.game.GameQueryService;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.GameView;
import com.github.saphyra.skyxplore_deprecated.game.service.game.GameViewConverter;
import com.github.saphyra.skyxplore_deprecated.game.service.game.creation.GameCreationService;
import com.github.saphyra.skyxplore_deprecated.game.service.game.deletion.GameDeletionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GameController {
    private static final String CREATE_GAME_MAPPING = API_PREFIX + "/game";
    private static final String DELETE_GAME_MAPPING = API_PREFIX + "/game/{gameId}";
    private static final String GET_GAMES_MAPPING = API_PREFIX + "/game";

    private final GameCreationService gameCreationService;
    private final GameQueryService gameQueryService;
    private final GameDeletionService gameDeletionService;
    private final GameViewConverter gameViewConverter;

    @PutMapping(CREATE_GAME_MAPPING)
    @Transactional
    public String createGame(
        @RequestBody OneStringParamRequest gameName
    ) {
        return gameCreationService.createGame(gameName.getValue());
    }

    @DeleteMapping(DELETE_GAME_MAPPING)
    @Transactional
    public void deleteGame(
        @PathVariable("gameId") UUID gameId
    ) {
        gameDeletionService.deleteByGameIdAndUserId(gameId);
    }

    @GetMapping(GET_GAMES_MAPPING)
    List<GameView> getGames() {
        return gameViewConverter.convertDomain(gameQueryService.getByUserId());
    }
}
