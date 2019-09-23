package com.github.saphyra.skyxplore.game.map.star.creation;

import com.github.saphyra.skyxplore.game.common.coordinates.Coordinate;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class CoordinateProvider {
    private final Random random;
    private final StarCreatorConfiguration configuration;

    List<Coordinate> getRandomCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < configuration.getCreationAttempts(); i++) {
            Coordinate coordinate = getRandomCoordinate();
            if (isPlaceable(result, coordinate)) {
                result.add(coordinate);
            }
        }
        return result;
    }

    private boolean isPlaceable(List<Coordinate> result, Coordinate coordinate) {
        return result.stream()
            .noneMatch(placedStar -> isTooClose(coordinate, placedStar));
    }

    private boolean isTooClose(Coordinate coordinate, Coordinate placedStar) {
        double distance = getDistance(placedStar, coordinate);
        return distance < configuration.getMinStarDistance();
    }

    private double getDistance(Coordinate placedStar, Coordinate coordinate) {
        int d1 = placedStar.getX() - coordinate.getX();
        int d2 = placedStar.getY() - coordinate.getY();

        int p1 = d1 * d1;
        int p2 = d2 * d2;
        return Math.sqrt(p1 + p2);
    }

    private Coordinate getRandomCoordinate() {
        return new Coordinate(random.randInt(0, configuration.getX()), random.randInt(0, configuration.getY()));
    }
}
