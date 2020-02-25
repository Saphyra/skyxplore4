package com.github.saphyra.skyxplore_deprecated.game.dao.map.star;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate.CoordinateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StarConverter extends ConverterBase<StarEntity, Star> {
    private final CoordinateConverter coordinateConverter;
    private final RequestContextHolder requestContextHolder;
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected Star processEntityConversion(StarEntity entity) {
        return Star.builder()
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starName(stringEncryptor.decryptEntity(entity.getStarName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
            .coordinate(coordinateConverter.convertEntity(entity.getCoordinates()))
            .ownerId(uuidConverter.convertEntity(entity.getOwnerId()))
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected StarEntity processDomainConversion(Star domain) {
        return StarEntity.builder()
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starName(stringEncryptor.encryptEntity(domain.getStarName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
            .coordinates(coordinateConverter.convertDomain(domain.getCoordinate()))
            .ownerId(uuidConverter.convertDomain(domain.getOwnerId()))
            .isNew(domain.isNew())
            .build();
    }
}
