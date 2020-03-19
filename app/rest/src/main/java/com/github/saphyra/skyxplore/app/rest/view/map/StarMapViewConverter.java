package com.github.saphyra.skyxplore.app.rest.view.map;

import com.github.saphyra.skyxplore.app.common.common_request.ViewConverter;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import org.springframework.stereotype.Component;

@Component
//TODO unit test
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
