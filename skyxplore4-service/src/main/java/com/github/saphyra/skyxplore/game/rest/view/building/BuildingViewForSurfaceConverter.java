package com.github.saphyra.skyxplore.game.rest.view.building;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.rest.view.ConstructionStatusView;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionViewQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BuildingViewForSurfaceConverter implements ViewConverter<Building, BuildingViewForSurface> {
    private final ConstructionViewQueryService constructionViewQueryService;
    private final GameDataQueryService gameDataQueryService;

    @Override
    public BuildingViewForSurface convertDomain(Building domain) {
        return BuildingViewForSurface.builder()
            .buildingId(domain.getBuildingId())
            .level(domain.getLevel())
            .maxLevel(gameDataQueryService.findBuildingData(domain.getBuildingDataId()).getConstructionRequirements().size())
            .dataId(domain.getBuildingDataId())
            .construction(getConstruction(domain))
            .build();
    }

    private ConstructionStatusView getConstruction(Building domain) {
        return Optional.ofNullable(domain.getConstructionId())
            .map(constructionViewQueryService::findByConstructionId)
            .orElse(null);
    }
}
