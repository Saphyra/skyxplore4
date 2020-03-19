package com.github.saphyra.skyxplore.app.service.game_creation.game;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.game.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameCreationService {

    private final DomainSaverService domainSaverService;
    private final GameFactory gameFactory;
    private final GameNameValidator gameNameValidator;

    public UUID createGame(String gameName, UUID userId) {
        gameNameValidator.validate(gameName);

        Game game = gameFactory.create(userId, gameName);
        domainSaverService.add(game);
        return game.getGameId();
    }
}
