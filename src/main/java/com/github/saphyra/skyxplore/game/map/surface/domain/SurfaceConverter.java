package com.github.saphyra.skyxplore.game.map.surface.domain;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.domain.CoordinateConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class SurfaceConverter extends ConverterBase<SurfaceEntity, Surface> {
    private final CoordinateConverter coordinateConverter;
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;


    @Override
    protected Surface processEntityConversion(SurfaceEntity surfaceEntity) {
        return Surface.builder()
            .surfaceId(uuidConverter.convertEntity(surfaceEntity.getSurfaceId()))
            .starId(uuidConverter.convertEntity(surfaceEntity.getStarId()))
            .userId(uuidConverter.convertEntity(surfaceEntity.getUserId()))
            .gameId(uuidConverter.convertEntity(surfaceEntity.getGameId()))
            .coordinate(coordinateConverter.convertEntity(surfaceEntity.getCoordinate(), surfaceEntity.getUserId()))
            .surfaceType(SurfaceType.valueOf(stringEncryptor.decryptEntity(surfaceEntity.getSurfaceType(), surfaceEntity.getUserId())))
            .buildingId(uuidConverter.convertEntity(surfaceEntity.getBuildingId()))
            .build();
    }

    @Override
    protected SurfaceEntity processDomainConversion(Surface surface) {
        return SurfaceEntity.builder()
            .surfaceId(uuidConverter.convertDomain(surface.getSurfaceId()))
            .starId(uuidConverter.convertDomain(surface.getStarId()))
            .userId(uuidConverter.convertDomain(surface.getUserId()))
            .gameId(uuidConverter.convertDomain(surface.getGameId()))
            .coordinate(coordinateConverter.convertDomain(surface.getCoordinate(), uuidConverter.convertDomain(surface.getUserId())))
            .surfaceType(stringEncryptor.encryptEntity(surface.getSurfaceType().name(), uuidConverter.convertDomain(surface.getUserId())))
            .buildingId(uuidConverter.convertDomain(surface.getBuildingId()))
            .build();
    }
}
