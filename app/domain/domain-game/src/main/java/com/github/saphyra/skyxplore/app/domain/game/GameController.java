package com.github.saphyra.skyxplore.app.domain.game;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameQueryService;
import com.github.saphyra.skyxplore.app.domain.game.service.GameCreationService;
import com.github.saphyra.skyxplore.app.domain.game.service.GameDeletionService;
import com.github.saphyra.skyxplore.app.domain.game.view.GameView;
import com.github.saphyra.skyxplore.app.domain.game.view.GameViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
//TODO FE test
public class GameController {
    private static final String CREATE_GAME_MAPPING = API_PREFIX + "/game";
    private static final String DELETE_GAME_MAPPING = API_PREFIX + "/game/{gameId}";
    private static final String GET_GAMES_MAPPING = API_PREFIX + "/game";

    private final GameCreationService gameCreationService;
    private final GameQueryService gameQueryService;
    private final GameDeletionService gameDeletionService;
    private final GameViewConverter gameViewConverter;
    private final UuidConverter uuidConverter;

    @PutMapping(CREATE_GAME_MAPPING)
    public String createGame(
        @RequestBody OneStringParamRequest gameName
    ) {
        return uuidConverter.convertDomain(gameCreationService.createGame(gameName.getValue()));
    }

    @DeleteMapping(DELETE_GAME_MAPPING)
    public void deleteGame(
        @PathVariable("gameId") UUID gameId
    ) {
        gameDeletionService.deleteGame(gameId);
    }

    @GetMapping(GET_GAMES_MAPPING)
    List<GameView> getGames() {
        return gameViewConverter.convertDomain(gameQueryService.getByUserId());
    }
}
