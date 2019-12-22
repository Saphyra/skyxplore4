package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SurfaceConverter extends ConverterBase<SurfaceEntity, Surface> {
    private final CoordinateConverter coordinateConverter;
    private final UuidConverter uuidConverter;


    @Override
    protected Surface processEntityConversion(SurfaceEntity surfaceEntity) {
        return Surface.builder()
            .surfaceId(uuidConverter.convertEntity(surfaceEntity.getSurfaceId()))
            .starId(uuidConverter.convertEntity(surfaceEntity.getStarId()))
            .userId(uuidConverter.convertEntity(surfaceEntity.getUserId()))
            .gameId(uuidConverter.convertEntity(surfaceEntity.getGameId()))
            .playerId(uuidConverter.convertEntity(surfaceEntity.getPlayerId()))
            .coordinate(coordinateConverter.convertEntity(surfaceEntity.getCoordinate(), surfaceEntity.getUserId()))
            .surfaceType(surfaceEntity.getSurfaceType())
            .build();
    }

    @Override
    protected SurfaceEntity processDomainConversion(Surface surface) {
        return SurfaceEntity.builder()
            .surfaceId(uuidConverter.convertDomain(surface.getSurfaceId()))
            .starId(uuidConverter.convertDomain(surface.getStarId()))
            .userId(uuidConverter.convertDomain(surface.getUserId()))
            .gameId(uuidConverter.convertDomain(surface.getGameId()))
            .playerId(uuidConverter.convertDomain(surface.getPlayerId()))
            .coordinate(coordinateConverter.convertDomain(surface.getCoordinate(), uuidConverter.convertDomain(surface.getUserId())))
            .surfaceType(surface.getSurfaceType())
            .build();
    }
}
