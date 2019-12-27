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
    protected Star processEntityConversion(StarEntity entity) {
        return Star.builder()
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starName(entity.getStarName())
            .coordinate(coordinateConverter.convertEntity(entity.getCoordinates()))
            .ownerId(uuidConverter.convertEntity(entity.getOwnerId()))
            .researches(researchConverter.convertEntity(entity.getResearches()))
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected StarEntity processDomainConversion(Star domain) {
        return StarEntity.builder()
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starName(domain.getStarName())
            .coordinates(coordinateConverter.convertDomain(domain.getCoordinate()))
            .ownerId(uuidConverter.convertDomain(domain.getOwnerId()))
            .researches(researchConverter.convertDomain(domain.getResearches()))
            .isNew(domain.isNew())
            .build();
    }
}
