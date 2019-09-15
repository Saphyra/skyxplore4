package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.game.domain.EncryptionGameRepository;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.view.GameView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GameController {
    private static final String CREATE_GAME_MAPPING = API_PREFIX + "/game";
    private static final String GET_GAMES_MAPPING = API_PREFIX + "/game";

    private final EncryptionGameRepository gameRepository;
    private final GameFactroy gameFactroy;
    private final GameViewConverter gameViewConverter;

    @PutMapping(CREATE_GAME_MAPPING)
    String createGame(
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
        @RequestBody OneStringParamRequest gameName
    ) {
        log.info("{} wants to create a game.", userId);
        log.debug("GameName: {}", gameName.getValue());
        Game game = gameFactroy.create(userId, gameName.getValue());
        gameRepository.save(game);
        return game.getGameId().toString();
    }

    @GetMapping(GET_GAMES_MAPPING)
    List<GameView> getGames(@CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId) {
        log.info("{} wants to know his games", userId);
        return gameViewConverter.convertDomain(gameRepository.getByUserId(userId));
    }
}
