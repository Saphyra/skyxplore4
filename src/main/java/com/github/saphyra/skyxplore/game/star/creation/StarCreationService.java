package com.github.saphyra.skyxplore.game.star.creation;

import com.github.saphyra.skyxplore.game.common.coordinates.Coordinate;
import com.github.saphyra.skyxplore.game.star.domain.Star;
import com.github.saphyra.skyxplore.game.star.domain.StarDao;
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
    private final CoordinateProvider coordinateProvider;
    private final IdGenerator idGenerator;
    private final StarDao starDao;
    private final StarNameService starNameService;

    public void createStars(UUID userId, UUID gameId) {
        List<Coordinate> coordinates = coordinateProvider.getRandomCoordinates();
        List<String> usedStarNames = new ArrayList<>();
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
            starDao.save(star);
        }
    }
}
