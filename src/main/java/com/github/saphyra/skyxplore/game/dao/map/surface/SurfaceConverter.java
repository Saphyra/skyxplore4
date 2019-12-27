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
    protected Surface processEntityConversion(SurfaceEntity entity) {
        return Surface.builder()
            .surfaceId(uuidConverter.convertEntity(entity.getSurfaceId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .coordinate(coordinateConverter.convertEntity(entity.getCoordinate()))
            .surfaceType(entity.getSurfaceType())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected SurfaceEntity processDomainConversion(Surface domain) {
        return SurfaceEntity.builder()
            .surfaceId(uuidConverter.convertDomain(domain.getSurfaceId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .coordinate(coordinateConverter.convertDomain(domain.getCoordinate()))
            .surfaceType(domain.getSurfaceType())
            .isNew(domain.isNew())
            .build();
    }
}
