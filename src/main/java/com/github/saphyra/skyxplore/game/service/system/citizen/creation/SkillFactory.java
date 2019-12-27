package com.github.saphyra.skyxplore.game.service.system.citizen.creation;

import com.github.saphyra.skyxplore.game.common.GameProperties;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class SkillFactory {
    private final GameProperties gameProperties;
    private final IdGenerator idGenerator;

    Skill create(SkillType skillType, UUID citizenId, UUID gameId) {
        return Skill.builder()
            .skillId(idGenerator.randomUUID())
            .citizenId(citizenId)
            .gameId(gameId)
            .skillType(skillType)
            .level(1)
            .experience(0)
            .nextLevel(gameProperties.getInitialNextLevel())
            .isNew(true)
            .build();
    }
}
