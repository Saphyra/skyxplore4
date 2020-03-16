package com.github.saphyra.skyxplore.app.domain.coordinate;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

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
