package com.github.saphyra.skyxplore.game.rest.view.connection;

import com.github.saphyra.skyxplore.game.dao.map.star.StarQueryService;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.map.connection.StarConnection;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StarConnectionViewConverter implements ViewConverter<StarConnection, StarConnectionView> {
    private final StarQueryService starQueryService;

    @Override
    public StarConnectionView convertDomain(StarConnection domain) {
        return StarConnectionView.builder()
            .coordinate1(starQueryService.getCoordinateOfStar(domain.getStar1()))
            .coordinate2(starQueryService.getCoordinateOfStar(domain.getStar2()))
            .build();
    }
}
