package com.github.saphyra.skyxplore.game.map.connection.creation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.StarsCreatedEvent;
import com.github.saphyra.skyxplore.game.common.coordinates.DistanceCalculator;
import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnection;
import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnectionDao;
import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ConnectionCreationService {
    private final ConnectionCreationConfiguration configuration;
    private final StarConnectionDao starConnectionDao;
    private final DistanceCalculator distanceCalculator;
    private final IdGenerator idGenerator;

    @EventListener
    void starsCreatedEventListener(StarsCreatedEvent event) {
        List<Star> stars = event.getStars();
        List<StarConnection> connections = new ArrayList<>();
        stars.forEach(star -> connections.addAll(connectCloseStars(star, stars)));
        connections.addAll(connectDistantStars(stars, connections));
        removeConnections(connections, stars);
        connections.forEach(starConnectionDao::save);
    }

    private List<StarConnection> connectCloseStars(Star star, List<Star> stars) {
        return stars.stream()
            .filter(targetStar -> !star.equals(targetStar))
            .filter(targetStar -> distanceCalculator.getDistance(star.getCoordinate(), targetStar.getCoordinate()) < configuration.getMaxDistance())
            .map(targetStar -> createConnection(star, targetStar))
            .collect(Collectors.toList());
    }

    private List<StarConnection> connectDistantStars(List<Star> stars, List<StarConnection> connections) {
        return stars.stream()
            .filter(star -> !hasConnection(star, connections))
            .map(star -> createConnection(star, getClosestStar(star, stars)))
            .collect(Collectors.toList());
    }

    private boolean hasConnection(Star star, List<StarConnection> connections) {
        return connections.stream()
            .anyMatch(starConnection -> starConnection.getStar1().equals(star.getStarId()) || starConnection.getStar2().equals(star.getStarId()));
    }

    private Star getClosestStar(Star star, List<Star> stars) {
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

    private StarConnection createConnection(Star star, Star targetStar) {
        return StarConnection.builder()
            .connectionId(idGenerator.randomUUID())
            .gameId(star.getGameId())
            .userId(star.getUserId())
            .star1(star.getStarId())
            .star2(targetStar.getStarId())
            .build();
    }

    private void removeConnections(List<StarConnection> connections, List<Star> stars) {
        for (Star star : stars) {
            while (getConnectionsOfStar(star, connections).size() > configuration.getMaxNumberOfConnections()) {
                StarConnection connectionToRemove = getConnectionsOfStar(star, connections).stream()
                    .max(Comparator.comparingInt(c -> getNumberOfConnections(star, c, connections)))
                    .orElseThrow(() -> new RuntimeException("Star has too many connections, but none to remove"));
                connections.remove(connectionToRemove);
            }
        }
    }

    private List<StarConnection> getConnectionsOfStar(Star star, List<StarConnection> connections) {
        return connections.stream()
            .filter(starConnection -> starConnection.getStar1().equals(star.getStarId()) || starConnection.getStar2().equals(star.getStarId()))
            .collect(Collectors.toList());
    }

    private int getNumberOfConnections(Star star, StarConnection connection, List<StarConnection> connections) {
        UUID starId = connection.getStar1().equals(star.getStarId()) ? connection.getStar2() : connection.getStar1();
        return Math.toIntExact(connections.stream()
            .filter(starConnection -> starConnection.getStar1().equals(starId) || starConnection.getStar2().equals(starId))
            .count());
    }
}
