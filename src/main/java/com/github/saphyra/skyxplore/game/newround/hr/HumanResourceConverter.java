package com.github.saphyra.skyxplore.game.newround.hr;

import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillQueryService;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class HumanResourceConverter {
    private final HumanResourceContext humanResourceContext;
    private final HumanResourceProperties humanResourceProperties;
    private final SkillQueryService skillQueryService;

    HumanResource convert(Citizen citizen) {
        return HumanResource.builder()
            .citizen(citizen)
            .context(humanResourceContext)
            .workPointsLeft(humanResourceProperties.getWorkPoints())
            .skills(getSkills(citizen.getCitizenId()))
            .build();
    }

    private Map<SkillType, Skill> getSkills(UUID citizenId) {
        return skillQueryService.getByCitizenId(citizenId)
            .stream()
            .collect(Collectors.toMap(Skill::getSkillType, Function.identity()));
    }
}
