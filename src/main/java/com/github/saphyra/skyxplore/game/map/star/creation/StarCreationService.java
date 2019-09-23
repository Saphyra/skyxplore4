package com.github.saphyra.skyxplore.game.map.star.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.event.StarsCreatedEvent;
import com.github.saphyra.skyxplore.game.common.coordinates.Coordinate;
import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import com.github.saphyra.skyxplore.game.map.star.domain.StarDao;
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
    private final StarDao starDao;
    private final StarNameService starNameService;

    public void createStars(UUID userId, UUID gameId) {
        List<Coordinate> coordinates = coordinateProvider.getRandomCoordinates();
        List<String> usedStarNames = new ArrayList<>();
        List<Star> createdStars = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            String starName = starNameService.getRandomStarName(usedStarNames);
            usedStarNames.add(starName);
            Star star = Star.builder()
                .starId(idGenerator.randomUUID())
                .gameId(gameId)
                .userId(userId)
                .starName(starName)
                .coordinate(coordinate)
                .build();
            createdStars.add(star);
            starDao.save(star);
        }
        applicationEventPublisher.publishEvent(new StarsCreatedEvent(createdStars));
    }
}
