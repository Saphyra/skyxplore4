package com.github.saphyra.skyxplore.game.map.surface.view;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.map.surface.domain.Surface;

@Component
public class SurfaceViewConverter implements ViewConverter<Surface, SurfaceView> {
    @Override
    public SurfaceView convertDomain(Surface domain) {
        return SurfaceView.builder()
            .surfaceId(domain.getSurfaceId())
            .coordinate(domain.getCoordinate())
            .surfaceType(domain.getSurfaceType())
            .buildingId(domain.getBuildingId())
            .build();
    }
}
