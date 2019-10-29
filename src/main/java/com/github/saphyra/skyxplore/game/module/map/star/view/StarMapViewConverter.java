package com.github.saphyra.skyxplore.game.module.map.star.view;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.module.map.star.domain.Star;

@Component
public class StarMapViewConverter implements ViewConverter<Star, StarMapView> {
    @Override
    public StarMapView convertDomain(Star domain) {
        return StarMapView.builder()
            .starId(domain.getStarId())
            .starName(domain.getStarName())
            .coordinate(domain.getCoordinate())
            .ownerId(domain.getOwnerId())
            .build();
    }
}
