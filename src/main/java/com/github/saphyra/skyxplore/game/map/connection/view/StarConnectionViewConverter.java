package com.github.saphyra.skyxplore.game.map.connection.view;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnection;
import com.github.saphyra.skyxplore.game.map.star.StarQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StarConnectionViewConverter implements ViewConverter<StarConnection, StarConnectionView> {
    private final StarQueryService starQueryService;

    @Override
    public StarConnectionView convertDomain(StarConnection domain) {
        return StarConnectionView.builder()
            .coordinate1(starQueryService.findByIdValidated(domain.getStar1(), domain.getUserId()).getCoordinate())
            .coordinate2(starQueryService.findByIdValidated(domain.getStar2(), domain.getUserId()).getCoordinate())
            .build();
    }
}
