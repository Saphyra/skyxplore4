package com.github.saphyra.skyxplore.game.service.map.star.creation;

import com.github.saphyra.skyxplore.data.gamedata.StarNames;
import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore.game.service.map.connection.creation.ConnectionCreationService;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.map.star.StarDao;
import com.github.saphyra.skyxplore.game.service.map.surface.creation.SurfaceCreationService;
import com.github.saphyra.skyxplore.game.service.player.PlayerService;
import com.github.saphyra.skyxplore.game.dao.player.Player;
import com.github.saphyra.skyxplore.game.service.system.citizen.service.creation.CitizenCreationService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class StarCreationService {
    private final CitizenCreationService citizenCreationService;
    private final ConnectionCreationService connectionCreationService;
    private final CoordinateProvider coordinateProvider;
    private final IdGenerator idGenerator;
    private final PlayerService playerService;
    private final StarDao starDao;
    private final StarNames starNames;
    private final SurfaceCreationService surfaceCreationService;

    public void createStars(UUID userId, UUID gameId) {
        log.info("Creating stars...");
        List<Coordinate> coordinates = coordinateProvider.getRandomCoordinates();
        List<String> usedStarNames = new ArrayList<>();
        List<String> usedPlayerNames = new ArrayList<>();
        List<Star> createdStars = new ArrayList<>();
        boolean isAi = false;

        for (Coordinate coordinate : coordinates) {
            String starName = starNames.getRandomStarName(usedStarNames);
            usedStarNames.add(starName);
            Player player = playerService.create(gameId, userId, isAi, usedPlayerNames);
            Star star = Star.builder()
                .starId(idGenerator.randomUUID())
                .gameId(gameId)
                .userId(userId)
                .starName(starName)
                .coordinate(coordinate)
                .ownerId(player.getPlayerId())
                .build();
            log.debug("Star created: {}", star);
            createdStars.add(star);
            usedPlayerNames.add(player.getPlayerName());
            isAi= true;
        }
        starDao.saveAll(createdStars);
        connectionCreationService.createConnections(createdStars);
        surfaceCreationService.createSurfaces(createdStars);
        citizenCreationService.createCitizens(createdStars);
    }
}