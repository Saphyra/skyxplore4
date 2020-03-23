package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class ConnectionReducer {
    private final DistanceCalculator distanceCalculator;
    private final StarConnectionCreationConfiguration configuration;

    List<StarConnection> removeConnections(List<StarConnection> connections, List<Star> stars) {
        for (Star star : stars) {
            while (getConnectionsOfStar(star.getStarId(), connections).size() > configuration.getMaxNumberOfConnections()) {
                List<StarConnection> connectionsOfStar = getConnectionsOfStar(star.getStarId(), connections);
                log.debug("{} has too many connections: {}", star.getStarId(), connectionsOfStar.size());
                StarConnection connectionToRemove = getConnectionToRemove(connections, star, connectionsOfStar, stars)
                    .orElseThrow(() -> new RuntimeException("Star has too many connections, but none to remove"));
                log.debug("Connection to remove: {}", connectionToRemove);
                connections.remove(connectionToRemove);
            }
        }
        return connections;
    }

    private Optional<StarConnection> getConnectionToRemove(List<StarConnection> connections, Star star, List<StarConnection> connectionsOfStar, List<Star> stars) {
        List<ConnectionDistance> connectionDistances = getCandidatesToRemove(connections, star, connectionsOfStar)
            .stream()
            .filter(starConnection -> bothStarsHasMultipleConnections(starConnection, connections))
            .map(starConnection -> new ConnectionDistance(starConnection, getDistanceOfConnection(starConnection, stars)))
            .collect(Collectors.toList());
        log.debug("ConnectionDistances: {}", connectionDistances);
        return connectionDistances.stream()
            .max(Comparator.comparingDouble(ConnectionDistance::getDistance))
            .map(ConnectionDistance::getConnection);
    }

    private double getDistanceOfConnection(StarConnection starConnection, List<Star> stars) {
        Star star1 = findStar(starConnection.getStar1(), stars);
        Star star2 = findStar(starConnection.getStar2(), stars);
        return distanceCalculator.getDistance(star1.getCoordinate(), star2.getCoordinate());
    }

    private Star findStar(UUID starId, List<Star> stars) {
        return stars.stream()
            .filter(star -> star.getStarId().equals(starId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No star found with id " + starId));
    }

    private List<StarConnection> getCandidatesToRemove(List<StarConnection> connections, Star star, List<StarConnection> connectionsOfStar) {
        log.debug("ConnectionsOfStar: {}", connectionsOfStar);
        List<StarConnection> result = new ArrayList<>();
        int numberOfConnections = 0;
        for (StarConnection connection : connectionsOfStar) {
            int numberOfConnectionsOfConnectedStar = getNumberOfConnectionsOfConnectedStar(star, connection, connections);

            if (numberOfConnectionsOfConnectedStar > numberOfConnections) {
                numberOfConnections = numberOfConnectionsOfConnectedStar;
                result = new ArrayList<>();
            }

            if (numberOfConnectionsOfConnectedStar == numberOfConnections) {
                result.add(connection);
            }
        }
        log.debug("Candidates to remove: {}", result);
        return result;
    }

    private boolean bothStarsHasMultipleConnections(StarConnection starConnection, List<StarConnection> connections) {
        return getConnectionsOfStar(starConnection.getStar1(), connections).size() > 1 && getConnectionsOfStar(starConnection.getStar2(), connections).size() > 1;
    }

    private List<StarConnection> getConnectionsOfStar(UUID starId, List<StarConnection> connections) {
        return connections.stream()
            .filter(starConnection -> starConnection.getStar1().equals(starId) || starConnection.getStar2().equals(starId))
            .collect(Collectors.toList());
    }

    private int getNumberOfConnectionsOfConnectedStar(Star star, StarConnection connection, List<StarConnection> connections) {
        UUID starId = connection.getStar1().equals(star.getStarId()) ? connection.getStar2() : connection.getStar1();
        return getConnectionsOfStar(starId, connections).size();
    }

    @Data
    private static class ConnectionDistance {
        private final StarConnection connection;
        private final double distance;

        @Override
        public String toString() {
            return connection.toString() + ": " + distance;
        }
    }
}
