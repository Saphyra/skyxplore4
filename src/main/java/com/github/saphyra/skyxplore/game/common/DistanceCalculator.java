package com.github.saphyra.skyxplore.game.common;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.common.domain.Coordinate;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistanceCalculator {
    public double getDistance(Coordinate c1, Coordinate c2) {
        int d1 = c1.getX() - c2.getX();
        int d2 = c1.getY() - c2.getY();

        int p1 = d1 * d1;
        int p2 = d2 * d2;
        double result = Math.sqrt(p1 + p2);
        log.debug("Distance between coordinates {}, {}: {}", c1, c2, result);
        return result;
    }
}
