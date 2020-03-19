package com.github.saphyra.skyxplore.app.service.game_creation.star;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class StarFactory {
    private final IdGenerator idGenerator;

    Star create(UUID gameId, String starName, Coordinate coordinate, UUID playerId) {
        return Star.builder()
            .starId(idGenerator.randomUUID())
            .gameId(gameId)
            .starName(starName)
            .coordinate(coordinate)
            .ownerId(playerId)
            .isNew(true)
            .build();
    }
}
