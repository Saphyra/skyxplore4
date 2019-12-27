package com.github.saphyra.skyxplore.game.service.map.star.creation;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class StarFactory {
    private final IdGenerator idGenerator;

    Star create(UUID gameId, UUID userId, String starName, Coordinate coordinate, UUID playerId) {
        return Star.builder()
            .starId(idGenerator.randomUUID())
            .gameId(gameId)
            .userId(userId)
            .starName(starName)
            .coordinate(coordinate)
            .ownerId(playerId)
            .researches(Collections.emptyList())
            .isNew(true)
            .build();
    }

}
