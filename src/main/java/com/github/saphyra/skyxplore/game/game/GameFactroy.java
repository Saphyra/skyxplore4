package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class GameFactroy {
    private final IdGenerator idGenerator;

    Game create(UUID userId, String gameName) {
        return Game.builder()
            .gameId(idGenerator.randomUUID())
            .userId(userId)
            .gameName(gameName)
            .build();
    }
}
