package com.github.saphyra.skyxplore.game.service.map.surface.creation;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class SurfaceFactory {
    private final IdGenerator idGenerator;

    Surface create(UUID starId, UUID gameId, UUID playerId, Coordinate coordinate, SurfaceType surfaceType) {
        return Surface.builder()
            .surfaceId(idGenerator.randomUUID())
            .starId(starId)
            .gameId(gameId)
            .playerId(playerId)
            .coordinate(coordinate)
            .surfaceType(surfaceType)
            .isNew(true)
            .build();
    }
}
