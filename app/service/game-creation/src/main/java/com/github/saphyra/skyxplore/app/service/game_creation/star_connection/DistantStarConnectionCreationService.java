package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class DistantStarConnectionCreationService {
    private final ClosestStarFinder closestStarFinder;
    private final StarConnectionFactory starConnectionFactory;

    List<StarConnection> connectDistantStars(List<Star> stars, List<StarConnection> connections) {
        if (stars.size() < 2) {
            return Collections.emptyList();
        }
        List<StarConnection> distantConnections = stars.stream()
            .filter(star -> !hasConnection(star, connections))
            .map(star -> starConnectionFactory.createConnection(star, closestStarFinder.getClosestStar(star, stars)))
            .collect(Collectors.toList());
        return Stream.concat(connections.stream(), distantConnections.stream())
            .collect(Collectors.toList());
    }

    private boolean hasConnection(Star star, List<StarConnection> connections) {
        boolean result = connections.stream()
            .anyMatch(starConnection -> starConnection.getStar1().equals(star.getStarId()) || starConnection.getStar2().equals(star.getStarId()));
        log.debug("Star {} has connections: {}", star, result);
        return result;
    }
}
