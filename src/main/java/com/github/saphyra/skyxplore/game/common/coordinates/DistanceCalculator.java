package com.github.saphyra.skyxplore.game.common.coordinates;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;

@Component
public class DistanceCalculator {
    public double getDistance(Coordinate c1, Coordinate c2) {
        int d1 = c1.getX() - c2.getX();
        int d2 = c1.getY() - c2.getY();

        int p1 = d1 * d1;
        int p2 = d2 * d2;
        return Math.sqrt(p1 + p2);
    }
}
