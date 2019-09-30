package com.github.saphyra.skyxplore.game.map.star.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.event.StarsCreatedEvent;
import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import com.github.saphyra.skyxplore.game.map.star.domain.StarDao;
import com.github.saphyra.skyxplore.game.player.PlayerService;
import com.github.saphyra.skyxplore.game.player.domain.Player;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StarCreationService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CoordinateProvider coordinateProvider;
    private final IdGenerator idGenerator;
    private final PlayerService playerService;
    private final StarDao starDao;
    private final StarNameService starNameService;

    public void createStars(UUID userId, UUID gameId) {
        List<Coordinate> coordinates = coordinateProvider.getRandomCoordinates();
        List<String> usedStarNames = new ArrayList<>();
        List<String> usedPlayerNames = new ArrayList<>();
        List<Star> createdStars = new ArrayList<>();
        boolean isAi = false;

        for (Coordinate coordinate : coordinates) {
            String starName = starNameService.getRandomStarName(usedStarNames);
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
            starDao.save(star);
            isAi= true;
        }
        applicationEventPublisher.publishEvent(new StarsCreatedEvent(createdStars));
    }
}
