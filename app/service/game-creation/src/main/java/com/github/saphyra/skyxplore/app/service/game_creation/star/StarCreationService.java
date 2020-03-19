package com.github.saphyra.skyxplore.app.service.game_creation.star;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.common.utils.Mapping;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.data.name.StarNames;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StarCreationService {
    private final DomainSaverService domainSaverService;
    private final StarFactory starFactory;
    private final StarNames starNames;

    public List<Star> createStars(UUID gameId, List<Mapping<Coordinate, UUID>> coordinatePlayerMapping) {
        List<String> usedStarNames = new ArrayList<>();
        List<Star> createdStars = new ArrayList<>();

        for (Mapping<Coordinate, UUID> mapping : coordinatePlayerMapping) {
            String starName = starNames.getRandomStarName(usedStarNames);
            usedStarNames.add(starName);
            Star star = starFactory.create(
                gameId,
                starName,
                mapping.getKey(),
                mapping.getValue()
            );
            log.debug("Star created: {}", star);
            createdStars.add(star);
        }
        domainSaverService.addAll(createdStars);

        return createdStars;
    }
}
