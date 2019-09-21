package com.github.saphyra.skyxplore.game.star.view;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.star.domain.Star;
import org.springframework.stereotype.Component;

@Component
public class StarViewConverter implements ViewConverter<Star, StarView> {
    @Override
    public StarView convertDomain(Star domain) {
        return StarView.builder()
            .starId(domain.getStarId())
            .starName(domain.getStarName())
            .coordinate(domain.getCoordinate())
            .build();
    }
}
