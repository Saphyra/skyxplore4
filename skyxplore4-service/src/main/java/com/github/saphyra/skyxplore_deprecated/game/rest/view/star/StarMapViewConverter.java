package com.github.saphyra.skyxplore_deprecated.game.rest.view.star;

import com.github.saphyra.skyxplore_deprecated.common.ViewConverter;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.Star;
import org.springframework.stereotype.Component;

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
