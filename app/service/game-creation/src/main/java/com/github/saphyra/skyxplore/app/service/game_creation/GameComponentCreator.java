package com.github.saphyra.skyxplore.app.service.game_creation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.common.utils.Mapping;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;
import com.github.saphyra.skyxplore.app.service.game_creation.game.GameCreationService;
import com.github.saphyra.skyxplore.app.service.game_creation.player.PlayerCreationService;
import com.github.saphyra.skyxplore.app.service.game_creation.star.CoordinateProvider;
import com.github.saphyra.skyxplore.app.service.game_creation.star.StarCreationService;
import com.github.saphyra.skyxplore.app.service.game_creation.star_connection.StarConnectionCreationService;
import com.github.saphyra.skyxplore.app.service.game_creation.surface.SurfaceCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameComponentCreator {
    //TODO restore after migration
    //private final CitizenCreationService citizenCreationService;
    //TODO restore after migration
    //private final PriorityCreationService priorityCreationService;
    //TODO restore when building is migrated
    //private final DefaultBuildingCreationService defaultBuildingCreationService;

    private final CoordinateProvider coordinateProvider;
    private final DomainSaverService domainSaverService;
    private final GameCreationService gameCreationService;
    private final PlayerCreationService playerCreationService;
    private final StarCreationService starCreationService;
    private final StarConnectionCreationService starConnectionCreationService;
    private final SurfaceCreationService surfaceCreationService;
    private final RequestContextHolder requestContextHolder;

    @Transactional
    public UUID createGame(String gameName) {
        try {
            StopWatch stopWatch = new StopWatch("GameCreation");
            stopWatch.start();
            log.debug("GameName: {}", gameName);

            UUID userId = requestContextHolder.get().getUserId();
            log.info("Creating new game for user {}", userId);
            log.debug("GameName: {}", gameName);

            UUID gameId = gameCreationService.createGame(gameName, userId);

            List<Coordinate> coordinates = coordinateProvider.getRandomCoordinates();
            List<Mapping<Coordinate, UUID>> coordinatePlayerIdMapping = playerCreationService.create(gameId, userId, coordinates);

            List<Star> createdStars = starCreationService.createStars(gameId, coordinatePlayerIdMapping);

            starConnectionCreationService.createConnections(createdStars);
            Map<Star, List<Surface>> surfaces = surfaceCreationService.createSurfaces(createdStars);
            //defaultBuildingCreationService.addDefaultBuildingsForSystem(surfaces);
            //priorityCreationService.createForStars(createdStars);
            //citizenCreationService.createCitizens(createdStars);

            domainSaverService.save();
            stopWatch.stop();
            log.info("Game created with gameId {} in {} seconds", gameId, stopWatch.getTotalTimeSeconds());
            return gameId;
        } finally {
            domainSaverService.clear();
        }
    }
}
