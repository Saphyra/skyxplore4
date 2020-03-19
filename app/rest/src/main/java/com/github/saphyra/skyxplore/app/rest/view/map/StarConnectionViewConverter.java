package com.github.saphyra.skyxplore.app.rest.view.map;

import com.github.saphyra.skyxplore.app.common.common_request.ViewConverter;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO unit test
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
