package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class SurfaceMapFactory {
    private final EmptySurfaceMapFactory emptySurfaceMapFactory;
    private final SurfaceMapFiller surfaceMapFiller;

    SurfaceType[][] createSurfaceMap() {
        SurfaceType[][] surfaceMap = emptySurfaceMapFactory.createEmptySurfaceMap();

        return surfaceMapFiller.fillSurfaceMap(surfaceMap);
    }
}
