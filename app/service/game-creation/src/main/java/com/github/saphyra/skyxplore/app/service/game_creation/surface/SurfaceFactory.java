package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

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
