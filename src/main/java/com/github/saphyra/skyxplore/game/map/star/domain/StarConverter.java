package com.github.saphyra.skyxplore.game.map.star.domain;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.coordinates.domain.CoordinateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StarConverter extends ConverterBase<StarEntity, Star> {
    private final CoordinateConverter coordinateConverter;
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected Star processEntityConversion(StarEntity starEntity) {
        return Star.builder()
            .starId(uuidConverter.convertEntity(starEntity.getStarId()))
            .gameId(uuidConverter.convertEntity(starEntity.getGameId()))
            .userId(uuidConverter.convertEntity(starEntity.getUserId()))
            .starName(stringEncryptor.decryptEntity(starEntity.getStarName(), starEntity.getUserId()))
            .coordinate(coordinateConverter.convertEntity(starEntity.getCoordinates(), starEntity.getUserId()))
            .build();
    }

    @Override
    protected StarEntity processDomainConversion(Star star) {
        return StarEntity.builder()
            .starId(uuidConverter.convertDomain(star.getStarId()))
            .gameId(uuidConverter.convertDomain(star.getGameId()))
            .userId(uuidConverter.convertDomain(star.getUserId()))
            .starName(stringEncryptor.encryptEntity(star.getStarName(), uuidConverter.convertDomain(star.getUserId())))
            .coordinates(coordinateConverter.convertDomain(star.getCoordinate(), uuidConverter.convertDomain(star.getUserId())))
            .build();
    }
}
