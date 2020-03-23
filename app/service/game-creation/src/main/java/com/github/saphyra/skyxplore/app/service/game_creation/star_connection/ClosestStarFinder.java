package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class ClosestStarFinder {
    private final DistanceCalculator distanceCalculator;

    Star getClosestStar(Star star, List<Star> stars) {
        return stars.stream()
            .filter(targetStar -> !targetStar.equals(star))
            .map(targetStar -> new StarDistanceWrapper(targetStar, distanceCalculator.getDistance(star.getCoordinate(), targetStar.getCoordinate())))
            .min((o1, o2) -> (int) (o1.getDistance() - o2.getDistance()))
            .map(StarDistanceWrapper::getStar)
            .orElseThrow(() -> new RuntimeException("No star was supplied"));
    }

    @Data
    private static class StarDistanceWrapper {
        private final Star star;
        private final double distance;
    }
}
