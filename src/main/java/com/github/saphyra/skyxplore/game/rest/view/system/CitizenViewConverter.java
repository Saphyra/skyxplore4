package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.Citizen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CitizenViewConverter implements ViewConverter<Citizen, CitizenView> {
    private final SkillViewConverter skillViewConverter;

    @Override
    public CitizenView convertDomain(Citizen domain) {
        return CitizenView.builder()
                .citizenId(domain.getCitizenId())
                .morale(domain.getMorale())
                .satiety(domain.getSatiety())
                .skills(skillViewConverter.convertDomain(new ArrayList<>(domain.getSkills().values())))
                .build();
    }
}
