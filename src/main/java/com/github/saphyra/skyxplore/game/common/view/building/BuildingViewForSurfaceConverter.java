package com.github.saphyra.skyxplore.game.common.view.building;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.system.building.domain.Building;
import org.springframework.stereotype.Component;

@Component
public class BuildingViewForSurfaceConverter implements ViewConverter<Building, BuildingViewForSurface> {
    @Override
    public BuildingViewForSurface convertDomain(Building domain) {
        return BuildingViewForSurface.builder()
                .buildingId(domain.getBuildingId())
                .level(domain.getLevel())
                .dataId(domain.getBuildingDataId())
                .build();
    }
}
