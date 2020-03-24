package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class RandomEmptySlotNextToSurfaceTypeProvider {
    private final EmptySlotNextToSurfaceTypeProvider emptySlotNextToSurfaceTypeProvider;
    private final Random random;

    Optional<Coordinate> getRandomEmptySlotNextToSurfaceType(SurfaceType[][] surfaceMap, SurfaceType surfaceType) {
        List<Coordinate> emptySlotsNextToSurfaceType = emptySlotNextToSurfaceTypeProvider.getEmptySlotsNextToSurfaceType(surfaceMap, surfaceType);

        if (emptySlotsNextToSurfaceType.isEmpty()) {
            log.debug("No more places for surfaceType {}", surfaceType);
            return Optional.empty();
        } else {
            Coordinate coordinate = emptySlotsNextToSurfaceType.get(random.randInt(0, emptySlotsNextToSurfaceType.size() - 1));
            log.debug("Random empty slot next to surfaceType {}: {}", surfaceType, coordinate);
            return Optional.of(coordinate);
        }
    }
}
