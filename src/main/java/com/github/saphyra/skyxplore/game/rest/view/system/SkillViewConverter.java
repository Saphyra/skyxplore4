package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillViewConverter implements ViewConverter<Skill, SkillView> {
    @Override
    public SkillView convertDomain(Skill domain) {
        return SkillView.builder()
            .skillType(domain.getSkillType())
            .level(domain.getLevel())
            .experience(domain.getExperience())
            .nextLevel(domain.getNextLevel())
            .build();
    }
}
