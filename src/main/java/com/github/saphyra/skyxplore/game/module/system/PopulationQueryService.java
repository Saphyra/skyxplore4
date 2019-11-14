package com.github.saphyra.skyxplore.game.module.system;

import com.github.saphyra.skyxplore.game.module.system.building.BuildingService;
import com.github.saphyra.skyxplore.game.module.system.citizen.CitizenQueryService;
import com.github.saphyra.skyxplore.game.dao.system.citizen.LocationType;
import com.github.saphyra.skyxplore.game.rest.view.system.PopulationView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PopulationQueryService {
    private final BuildingService buildingService;
    private final CitizenQueryService citizenQueryService;

    public PopulationView getPopulationOfStar(UUID starId) {
        return PopulationView.builder()
                .dwellingSpaceAmount(buildingService.getDwellingSpaceAmount(starId))
                .citizenNum(citizenQueryService.countByLocation(LocationType.SYSTEM, starId))
                .build();
    }
}
