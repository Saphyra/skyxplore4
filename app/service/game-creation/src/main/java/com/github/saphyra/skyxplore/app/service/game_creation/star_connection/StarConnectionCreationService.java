package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarConnectionCreationService {
    private final CloseStarsConnectionCreationService closeStarsConnectionCreationService;
    private final ConnectionReducer connectionReducer;
    private final DistantStarConnectionCreationService distantStarConnectionCreationService;
    private final DomainSaverService domainSaverService;

    public void createConnections(List<Star> stars) {
        log.info("Creating StarConnections...");
        List<StarConnection> closeStarConnections = closeStarsConnectionCreationService.connectCloseStars(stars);
        List<StarConnection> distantStarConnections = distantStarConnectionCreationService.connectDistantStars(stars, closeStarConnections);
        List<StarConnection> reducedConnections = connectionReducer.removeConnections(distantStarConnections, stars);
        log.info("Number of connections: {}", reducedConnections.size());
        domainSaverService.addAll(reducedConnections);
    }
}
