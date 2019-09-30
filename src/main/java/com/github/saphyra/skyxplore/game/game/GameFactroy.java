package com.github.saphyra.skyxplore.game.game;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class GameFactroy {
    private final IdGenerator idGenerator;

    Game create(UUID userId, String gameName) {
        Game game = Game.builder()
            .gameId(idGenerator.randomUUID())
            .userId(userId)
            .gameName(gameName)
            .build();
        log.debug("Game created: {}", game);
        return game;
    }
}
