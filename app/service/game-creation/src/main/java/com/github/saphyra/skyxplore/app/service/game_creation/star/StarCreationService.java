package com.github.saphyra.skyxplore.app.service.game_creation.star;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.data.name.StarNames;
import com.github.saphyra.skyxplore.app.domain.player.Player;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.service.game_creation.player.PlayerCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class StarCreationService {
    //TODO restore after migration
    //private final CitizenCreationService citizenCreationService;
    //TODO restore after migration
    //private final ConnectionCreationService connectionCreationService;
    private final CoordinateProvider coordinateProvider;
    private final DomainSaverService domainSaverService;
    private final PlayerCreationService playerCreationService;
    //TODO restore after migration
    //private final PriorityCreationService priorityCreationService;
    private final StarFactory starFactory;
    private final StarNames starNames;
    //TODO restore after migration
    //private final SurfaceCreationService surfaceCreationService;

    public void createStars(UUID userId, UUID gameId) {
        log.info("Creating stars for gameId {} and userId {}...", gameId, userId);
        List<Coordinate> coordinates = coordinateProvider.getRandomCoordinates();
        List<String> usedStarNames = new ArrayList<>();
        List<String> usedPlayerNames = new ArrayList<>();
        List<Star> createdStars = new ArrayList<>();
        boolean isAi = false;

        for (Coordinate coordinate : coordinates) {
            String starName = starNames.getRandomStarName(usedStarNames);
            usedStarNames.add(starName);
            Player player = playerCreationService.create(gameId, userId, isAi, usedPlayerNames);
            Star star = starFactory.create(
                gameId,
                starName,
                coordinate,
                player.getPlayerId()
            );
            log.debug("Star created: {}", star);
            createdStars.add(star);
            usedPlayerNames.add(player.getPlayerName());
            isAi = true;
        }
        domainSaverService.addAll(createdStars);

        //connectionCreationService.createConnections(createdStars);
        //priorityCreationService.createForStars(createdStars);
        //surfaceCreationService.createSurfaces(createdStars);
        //citizenCreationService.createCitizens(createdStars);
    }
}
