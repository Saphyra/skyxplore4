package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class CloseStarsConnectionCreationService {
    private final StarConnectionCreationConfiguration configuration;
    private final DistanceCalculator distanceCalculator;
    private final StarConnectionFactory starConnectionFactory;

    List<StarConnection> connectCloseStars(List<Star> stars) {
        List<StarConnection> result = new ArrayList<>();

        stars.forEach(star -> result.addAll(createConnectionsForStar(star, stars)));
        return result;
    }

    private List<StarConnection> createConnectionsForStar(Star star, List<Star> stars) {
        return stars.stream()
            .filter(targetStar -> !star.equals(targetStar))
            .filter(targetStar -> distanceCalculator.getDistance(star.getCoordinate(), targetStar.getCoordinate()) < configuration.getMaxDistance())
            .map(targetStar -> starConnectionFactory.createConnection(star, targetStar))
            .collect(Collectors.toList());
    }
}
