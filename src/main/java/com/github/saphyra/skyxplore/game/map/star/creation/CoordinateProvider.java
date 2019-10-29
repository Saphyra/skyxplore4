package com.github.saphyra.skyxplore.game.map.star.creation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.common.DistanceCalculator;
import com.github.saphyra.skyxplore.game.common.domain.Coordinate;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class CoordinateProvider {
    private final DistanceCalculator distanceCalculator;
    private final Random random;
    private final StarCreatorConfiguration configuration;

    List<Coordinate> getRandomCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < configuration.getCreationAttempts(); i++) {
            Coordinate coordinate = getRandomCoordinate();
            if (isPlaceable(result, coordinate)) {
                log.debug("Placeable coordinate found: {} for attempt: {}", coordinate, i);
                result.add(coordinate);
            }
        }
        log.info("Number of placeable star coordinates: {}", result.size());
        return result;
    }

    private boolean isPlaceable(List<Coordinate> result, Coordinate coordinate) {
        return result.stream()
            .noneMatch(placedStar -> isTooClose(coordinate, placedStar));
    }

    private boolean isTooClose(Coordinate coordinate, Coordinate placedStar) {
        double distance = distanceCalculator.getDistance(placedStar, coordinate);
        return distance < configuration.getMinStarDistance();
    }



    private Coordinate getRandomCoordinate() {
        return new Coordinate(random.randInt(0, configuration.getX()), random.randInt(0, configuration.getY()));
    }
}
