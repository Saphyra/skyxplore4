package com.github.saphyra.skyxplore.app.service.game_creation.game;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.game.Game;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class GameFactory {
    private final IdGenerator idGenerator;

    Game create(UUID userId, String gameName) {
        Game game = Game.builder()
            .gameId(idGenerator.randomUUID())
            .userId(userId)
            .gameName(gameName)
            .round(1)
            .build();
        log.debug("Game created: {}", game);
        return game;
    }
}
