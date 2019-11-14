package com.github.saphyra.skyxplore.game.rest.view.star;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;

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
