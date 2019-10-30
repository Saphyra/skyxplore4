package com.github.saphyra.skyxplore.game.module.system;

import com.github.saphyra.skyxplore.game.module.system.building.BuildingService;
import com.github.saphyra.skyxplore.game.module.system.citizen.CitizenQueryService;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.Citizen;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.LocationType;
import com.github.saphyra.skyxplore.game.rest.view.system.CitizenViewConverter;
import com.github.saphyra.skyxplore.game.rest.view.system.PopulationView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PopulationQueryService {
    private final BuildingService buildingService;
    private final CitizenQueryService citizenQueryService;
    private final CitizenViewConverter citizenViewConverter;

    public PopulationView getPopulationOfStar(UUID starId) {
        List<Citizen> citizens = citizenQueryService.getByLocation(starId, LocationType.SYSTEM);
        return PopulationView.builder()
                .dwellingSpaceAmount(buildingService.getDwellingSpaceAmount(starId))
                .citizenNum(citizens.size())
                .citizens(citizenViewConverter.convertDomain(citizens))
                .build();
    }
}
