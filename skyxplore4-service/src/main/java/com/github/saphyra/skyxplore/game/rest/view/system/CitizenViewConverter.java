package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CitizenViewConverter implements ViewConverter<Citizen, CitizenView> {
    private final SkillQueryService skillQueryService;
    private final SkillViewConverter skillViewConverter;

    @Override
    public CitizenView convertDomain(Citizen domain) {
        return CitizenView.builder()
            .citizenId(domain.getCitizenId())
            .name(domain.getCitizenName())
            .morale(domain.getMorale())
            .satiety(domain.getSatiety())
            .skills(skillViewConverter.convertDomain(skillQueryService.getByCitizenId(domain.getCitizenId())))
            .build();
    }
}
