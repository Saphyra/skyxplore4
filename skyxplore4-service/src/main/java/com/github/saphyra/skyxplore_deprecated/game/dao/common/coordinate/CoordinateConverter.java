package com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CoordinateConverter {
    public Coordinate convertEntity(CoordinateEntity entity) {
        return Coordinate.builder()
            .x(entity.getX())
            .y(entity.getY())
            .build();
    }

    public CoordinateEntity convertDomain(Coordinate domain) {
        return CoordinateEntity.builder()
            .x(domain.getX())
            .y(domain.getY())
            .build();
    }
}
