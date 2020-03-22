package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ConnectionReducer {
    private final StarConnectionCreationConfiguration configuration;

    List<StarConnection> removeConnections(List<StarConnection> connections, List<Star> stars) {
        for (Star star : stars) {
            while (getConnectionsOfStar(star.getStarId(), connections).size() > configuration.getMaxNumberOfConnections()) {
                List<StarConnection> connectionsOfStar = getConnectionsOfStar(star.getStarId(), connections);
                log.debug("{} has too many connections: {}", star.getStarId(), connectionsOfStar.size());
                StarConnection connectionToRemove = connectionsOfStar.stream()
                    .max(Comparator.comparingInt(connection -> getNumberOfConnectionsOfConnectedStar(star, connection, connections)))
                    .filter(starConnection -> bothStarsHasMultipleConnections(starConnection, connections))
                    .orElseThrow(() -> new RuntimeException("Star has too many connections, but none to remove"));
                log.debug("Connection to remove: {}", connectionToRemove);
                connections.remove(connectionToRemove);
            }
        }
        return connections;
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
}
