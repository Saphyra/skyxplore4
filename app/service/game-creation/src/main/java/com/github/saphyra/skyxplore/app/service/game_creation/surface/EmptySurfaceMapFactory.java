package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class EmptySurfaceMapFactory {
    private final Random random;
    private final SurfaceCreationProperties properties;

    @SuppressWarnings("ExplicitArrayFilling")
    SurfaceType[][] createEmptySurfaceMap() {
        int surfaceSize = random.randInt(properties.getMinSize(), properties.getMaxSize());
        log.debug("surfaceSize: {}", surfaceSize);
        SurfaceType[][] surfaceMap = new SurfaceType[surfaceSize][surfaceSize];
        for (int x = 0; x < surfaceMap.length; x++) {
            surfaceMap[x] = new SurfaceType[surfaceSize];
        }
        return surfaceMap;
    }
}
