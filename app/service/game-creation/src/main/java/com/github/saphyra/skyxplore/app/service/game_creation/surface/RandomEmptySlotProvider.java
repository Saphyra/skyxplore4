package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.saphyra.skyxplore.app.service.game_creation.surface.SlotUtil.isEmptySlot;

@Component
@Slf4j
@RequiredArgsConstructor
class RandomEmptySlotProvider {
    private final Random random;

    Optional<Coordinate> getRandomEmptySlot(SurfaceType[][] surfaceMap) {
        Coordinate coordinate;
        do {
            coordinate = new Coordinate(random.randInt(0, surfaceMap.length - 1), random.randInt(0, surfaceMap.length - 1));
        } while (!isEmptySlot(surfaceMap, coordinate));
        log.debug("Random empty slot selected: {}", coordinate);
        return Optional.of(coordinate);
    }
}
