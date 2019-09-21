package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.GameDao;
import com.github.saphyra.skyxplore.game.game.view.GameView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GameController {
    private static final String CREATE_GAME_MAPPING = API_PREFIX + "/game";
    private static final String DELETE_GAME_MAPPING = API_PREFIX + "/game/{gameId}";
    private static final String GET_GAMES_MAPPING = API_PREFIX + "/game";

    private final ApplicationEventPublisher eventPublisher;
    private final GameDao gameDao;
    private final GameFactroy gameFactroy;
    private final GameViewConverter gameViewConverter;
    private final UuidConverter uuidConverter;

    @PutMapping(CREATE_GAME_MAPPING)
    String createGame(
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
        @RequestBody OneStringParamRequest gameName
    ) {
        log.info("{} wants to create a game.", userId);
        log.debug("GameName: {}", gameName.getValue());
        Game game = gameFactroy.create(userId, gameName.getValue());
        gameDao.save(game);
        eventPublisher.publishEvent(new GameCreatedEvent(userId, game.getGameId()));
        return game.getGameId().toString();
    }

    @DeleteMapping(DELETE_GAME_MAPPING)
    @Transactional
    public void deleteGame(
        @PathVariable("gameId") UUID gameId,
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId
    ){
        log.info("{} wants to delete game {}", userId, gameId);
        Optional<Game> gameOptional = gameDao.findById(uuidConverter.convertDomain(gameId));
        if(gameOptional.isPresent()){
            Game game = gameOptional.get();
            if(!game.getUserId().equals(userId)){
                throw ExceptionFactory.invalidGameAccess(userId, gameId);
            }
            gameDao.delete(game);
            eventPublisher.publishEvent(new GameDeletedEvent(userId, gameId));
        }else {
            throw ExceptionFactory.gameNotFound(gameId);
        }
    }

    @GetMapping(GET_GAMES_MAPPING)
    List<GameView> getGames(@CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId) {
        log.info("{} wants to know his games", userId);
        return gameViewConverter.convertDomain(gameDao.getByUserId(userId));
    }
}
