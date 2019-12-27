package com.github.saphyra.skyxplore.game.service.system.citizen.service.creation;

import com.github.saphyra.skyxplore.data.gamedata.FirstNames;
import com.github.saphyra.skyxplore.data.gamedata.LastNames;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.LocationType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CitizenFactory {
    private final FirstNames firstNames;
    private final IdGenerator idGenerator;
    private final LastNames lastNames;

    public Citizen create(Star star) {
        UUID citizenId = idGenerator.randomUUID();
        return Citizen.builder()
            .citizenId(citizenId)
            .citizenName(generateName())
            .gameId(star.getGameId())
            .userId(star.getUserId())
            .ownerId(star.getOwnerId())
            .locationType(LocationType.SYSTEM)
            .locationId(star.getStarId())
            .morale(100)
            .satiety(100)
            .build();
    }

    private String generateName() {
        return String.join(" ", lastNames.getRandom(), firstNames.getRandom());
    }
}
