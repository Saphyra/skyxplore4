package com.github.saphyra.skyxplore.game.rest.view.surface;

import com.github.saphyra.skyxplore.game.rest.view.ConstructionStatusView;
import com.github.saphyra.skyxplore.game.rest.view.building.BuildingViewForSurface;
import com.github.saphyra.skyxplore.game.rest.view.building.BuildingViewForSurfaceConverter;
import com.github.saphyra.skyxplore.game.service.system.building.BuildingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SurfaceViewConverter implements ViewConverter<Surface, SurfaceView> {
    private final BuildingQueryService buildingQueryService;
    private final BuildingViewForSurfaceConverter buildingViewForSurfaceConverter;

    @Override
    public SurfaceView convertDomain(Surface domain) {
        return SurfaceView.builder()
            .surfaceId(domain.getSurfaceId())
            .coordinate(domain.getCoordinate())
            .surfaceType(domain.getSurfaceType())
            .building(getBuilding(domain))
            .constructionStatusView(getTerraformStatus(domain))
            .build();
    }

    private BuildingViewForSurface getBuilding(Surface domain) {
        return Optional.ofNullable(domain.getBuildingId())
            .map(buildingQueryService::findOneValidated)
            .map(buildingViewForSurfaceConverter::convertDomain)
            .orElse(null);
    }

    private ConstructionStatusView getTerraformStatus(Surface domain) {
        //TODO implement
        return null;
    }
}
