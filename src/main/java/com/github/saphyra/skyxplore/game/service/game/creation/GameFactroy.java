package com.github.saphyra.skyxplore.game.service.game.creation;

import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
            .round(1)
            .build();
        log.debug("Game created: {}", game);
        return game;
    }
}
