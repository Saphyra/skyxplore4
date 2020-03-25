package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class SurfaceFiller {
    private final RandomEmptySlotNextToSurfaceTypeProvider randomEmptySlotNextToSurfaceTypeProvider;
    private final RandomEmptySlotProvider randomEmptySlotProvider;

    void fillBlockWithSurfaceType(SurfaceType[][] surfaceMap, SurfaceType surfaceType, boolean initialPlacement) {
        Optional<Coordinate> coordinateOptional = initialPlacement ? randomEmptySlotProvider.getRandomEmptySlot(surfaceMap) : randomEmptySlotNextToSurfaceTypeProvider.getRandomEmptySlotNextToSurfaceType(surfaceMap, surfaceType);
        coordinateOptional.ifPresent(coordinate -> {
            surfaceMap[coordinate.getX()][coordinate.getY()] = surfaceType;
            log.debug("Coordinate {} filled with surfaceType {}. surfaceMap: {}", coordinate, surfaceType, surfaceMap);
        });
    }
}
