package com.github.saphyra.skyxplore_deprecated.game.service.system;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.CitizenQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.LocationType;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.PopulationView;
import com.github.saphyra.skyxplore_deprecated.game.service.system.building.BuildingService;
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
            .citizenNum(citizenQueryService.countByLocationAndOwnerId(LocationType.SYSTEM, starId))
            .build();
    }
}
