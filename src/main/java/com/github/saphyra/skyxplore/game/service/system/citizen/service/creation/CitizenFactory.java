package com.github.saphyra.skyxplore.game.service.system.citizen.service.creation;

import com.github.saphyra.skyxplore.data.gamedata.FirstNames;
import com.github.saphyra.skyxplore.data.gamedata.LastNames;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.LocationType;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CitizenFactory {
    private final FirstNames firstNames;
    private final IdGenerator idGenerator;
    private final LastNames lastNames;
    private final SkillFactory skillFactory;

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
            .skills(generateSkills(citizenId))
            .build();
    }

    private Map<SkillType, Skill> generateSkills(UUID citizenId) {
        return Arrays.stream(SkillType.values())
            .collect(Collectors.toMap(skillType -> skillType, skillType -> skillFactory.create(skillType, citizenId)));
    }

    private String generateName() {
        return String.join(" ", lastNames.getRandom(), firstNames.getRandom());
    }
}
