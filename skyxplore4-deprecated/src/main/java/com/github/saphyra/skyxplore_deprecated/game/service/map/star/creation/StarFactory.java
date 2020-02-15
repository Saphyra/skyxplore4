package com.github.saphyra.skyxplore_deprecated.game.service.map.star.creation;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.Star;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
