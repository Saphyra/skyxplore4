package com.github.saphyra.skyxplore.game.common.view.surface;

import com.github.saphyra.skyxplore.game.common.view.building.BuildingViewForSurface;
import com.github.saphyra.skyxplore.game.common.view.building.BuildingViewForSurfaceConverter;
import com.github.saphyra.skyxplore.game.module.system.building.BuildingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.Surface;

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
                .build();
    }

    private BuildingViewForSurface getBuilding(Surface domain) {
        return Optional.ofNullable(domain.getBuildingId())
                .map(buildingQueryService::findOneValidated)
                .map(buildingViewForSurfaceConverter::convertDomain)
                .orElse(null);
    }
}
