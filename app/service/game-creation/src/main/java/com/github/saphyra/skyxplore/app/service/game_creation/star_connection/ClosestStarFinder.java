package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ClosestStarFinder {
    private final DistanceCalculator distanceCalculator;


    Star getClosestStar(Star star, List<Star> stars) {
        double minDistance = Integer.MAX_VALUE;
        Star result = null;
        for (Star targetStar : stars) {
            if (targetStar.equals(star)) {
                continue;
            }
            double d = distanceCalculator.getDistance(star.getCoordinate(), targetStar.getCoordinate());
            if (d < minDistance) {
                minDistance = d;
                result = targetStar;
            }
        }

        return result;
    }
}
