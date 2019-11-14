package com.github.saphyra.skyxplore.game.rest.view.building;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
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
