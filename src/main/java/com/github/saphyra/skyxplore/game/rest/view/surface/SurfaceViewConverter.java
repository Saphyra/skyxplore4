package com.github.saphyra.skyxplore.game.rest.view.surface;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.rest.view.ConstructionStatusView;
import com.github.saphyra.skyxplore.game.rest.view.building.BuildingViewForSurface;
import com.github.saphyra.skyxplore.game.rest.view.building.BuildingViewForSurfaceConverter;
import com.github.saphyra.skyxplore.game.service.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionViewQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurfaceViewConverter implements ViewConverter<Surface, SurfaceView> {
    private final BuildingQueryService buildingQueryService;
    private final BuildingViewForSurfaceConverter buildingViewForSurfaceConverter;
    private final ConstructionQueryService constructionQueryService;
    private final ConstructionViewQueryService constructionViewQueryService;

    @Override
    public SurfaceView convertDomain(Surface domain) {
        return SurfaceView.builder()
            .surfaceId(domain.getSurfaceId())
            .coordinate(domain.getCoordinate())
            .surfaceType(domain.getSurfaceType())
            .building(getBuilding(domain))
            .terraformStatus(getTerraformStatus(domain))
            .build();
    }

    private BuildingViewForSurface getBuilding(Surface surface) {
        return buildingQueryService.findBySurfaceId(surface.getSurfaceId())
            .map(buildingViewForSurfaceConverter::convertDomain)
            .orElse(null);
    }

    private ConstructionStatusView getTerraformStatus(Surface surface) {
        return constructionQueryService.findByConstructionTypeAndExternalId(ConstructionType.TERRAFORMING, surface.getSurfaceId())
            .map(constructionViewQueryService::findByConstruction)
            .orElse(null);
    }
}