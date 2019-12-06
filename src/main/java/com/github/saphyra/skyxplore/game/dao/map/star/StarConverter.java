package com.github.saphyra.skyxplore.game.dao.map.star;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StarConverter extends ConverterBase<StarEntity, Star> {
    private final CoordinateConverter coordinateConverter;
    private final ResearchConverter researchConverter;
    private final UuidConverter uuidConverter;

    @Override
    protected Star processEntityConversion(StarEntity starEntity) {
        return Star.builder()
            .starId(uuidConverter.convertEntity(starEntity.getStarId()))
            .gameId(uuidConverter.convertEntity(starEntity.getGameId()))
            .userId(uuidConverter.convertEntity(starEntity.getUserId()))
            .starName(starEntity.getStarName())
            .coordinate(coordinateConverter.convertEntity(starEntity.getCoordinates(), starEntity.getUserId()))
            .ownerId(uuidConverter.convertEntity(starEntity.getOwnerId()))
            .researches(researchConverter.convertEntity(starEntity.getResearches()))
            .build();
    }

    @Override
    protected StarEntity processDomainConversion(Star star) {
        return StarEntity.builder()
            .starId(uuidConverter.convertDomain(star.getStarId()))
            .gameId(uuidConverter.convertDomain(star.getGameId()))
            .userId(uuidConverter.convertDomain(star.getUserId()))
            .starName(star.getStarName())
            .coordinates(coordinateConverter.convertDomain(star.getCoordinate(), uuidConverter.convertDomain(star.getUserId())))
            .ownerId(uuidConverter.convertDomain(star.getOwnerId()))
            .researches(researchConverter.convertDomain(star.getResearches()))
            .build();
    }
}
