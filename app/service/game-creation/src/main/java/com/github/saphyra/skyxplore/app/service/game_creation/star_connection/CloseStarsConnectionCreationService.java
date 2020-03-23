package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class CloseStarsConnectionCreationService {
    private final StarConnectionCreationConfiguration configuration;
    private final DistanceCalculator distanceCalculator;
    private final StarConnectionFactory starConnectionFactory;

    List<StarConnection> connectCloseStars(List<Star> stars) {
        List<StarConnection> result = new ArrayList<>();

        stars.forEach(star -> result.addAll(createConnectionsForStar(star, stars, result)));
        return result;
    }

    private List<StarConnection> createConnectionsForStar(Star star, List<Star> stars, List<StarConnection> connections) {
        return stars.stream()
            .filter(targetStar -> !star.equals(targetStar))
            .filter(targetStar -> distanceCalculator.getDistance(star.getCoordinate(), targetStar.getCoordinate()) < configuration.getMaxDistance())
            .filter(targetStar -> !alreadyConnected(star, targetStar, connections))
            .map(targetStar -> starConnectionFactory.createConnection(star, targetStar))
            .collect(Collectors.toList());
    }

    private boolean alreadyConnected(Star star, Star targetStar, List<StarConnection> connections) {
        return connections.stream()
            .anyMatch(starConnection -> starsConnected(star, targetStar, starConnection));
    }

    private boolean starsConnected(Star star, Star targetStar, StarConnection starConnection) {
        return (starConnection.getStar1().equals(star.getStarId()) && starConnection.getStar2().equals(targetStar.getStarId()))
            || (starConnection.getStar1().equals(targetStar.getStarId()) && starConnection.getStar2().equals(star.getStarId()));
    }
}
