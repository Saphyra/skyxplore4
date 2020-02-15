package com.github.saphyra.skyxplore_deprecated.game.rest.view.system;

import com.github.saphyra.skyxplore_deprecated.common.ViewConverter;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.Skill;
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
