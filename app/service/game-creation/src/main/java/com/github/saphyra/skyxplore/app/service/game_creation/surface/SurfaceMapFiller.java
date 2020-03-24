package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class SurfaceMapFiller {
    private final SurfaceFiller surfaceFiller;
    private final SurfaceTypeListFactory surfaceTypeListFactory;

    SurfaceType[][] fillSurfaceMap(SurfaceType[][] surfaceMap) {
        boolean initialPlacement = true;
        do {
            List<SurfaceType> surfaceTypeList = surfaceTypeListFactory.createSurfaceTypeList(initialPlacement);
            boolean ip = initialPlacement;
            surfaceTypeList.forEach(surfaceType -> surfaceFiller.fillBlockWithSurfaceType(surfaceMap, surfaceType, ip));
            initialPlacement = false;
        } while (hasNullFields(surfaceMap));

        return surfaceMap;
    }

    private boolean hasNullFields(SurfaceType[][] surfaceMap) {
        for (SurfaceType[] surfaceTypes : surfaceMap) {
            for (SurfaceType surfaceType : surfaceTypes) {
                if (isNull(surfaceType)) {
                    log.debug("There are empty fields.");
                    return true;
                }
            }
        }
        log.debug("No more empty fields");
        return false;
    }
}
