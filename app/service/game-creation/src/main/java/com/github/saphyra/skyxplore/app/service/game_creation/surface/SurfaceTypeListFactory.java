package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
class SurfaceTypeListFactory {
    private final Random random;
    private final SurfaceCreationProperties properties;

    List<SurfaceType> createSurfaceTypeList(boolean initialPlacement) {
        List<SurfaceCreationProperties.SurfaceTypeSpawnDetails> allSpawnDetails = properties.getSurfaceTypeSpawnDetails();
        List<SurfaceType> result = allSpawnDetails.stream()
            .flatMap(surfaceTypeSpawnDetails -> Stream.generate(surfaceTypeSpawnDetails::getSurfaceName).limit(surfaceTypeSpawnDetails.getSpawnRate()))
            .map(SurfaceType::valueOf)
            .collect(Collectors.toList());
        Collections.shuffle(result);

        if (initialPlacement) {
            log.debug("Initial placement. Filtering out optional surfaceTypes");
            List<SurfaceCreationProperties.SurfaceTypeSpawnDetails> optionalSpawnDetails = allSpawnDetails.stream()
                .filter(SurfaceCreationProperties.SurfaceTypeSpawnDetails::isOptional)
                .collect(Collectors.toList());

            optionalSpawnDetails.stream()
                .filter((s) -> random.randBoolean())
                .peek(surfaceTypeSpawnDetails -> log.debug("SurfaceType {} will not spawn", surfaceTypeSpawnDetails.getSurfaceName()))
                .forEach(surfaceTypeSpawnDetails -> result.removeIf(surfaceType -> surfaceType.equals(SurfaceType.valueOf(surfaceTypeSpawnDetails.getSurfaceName()))));

        }
        log.debug("surfaceTypeList: {}", result);
        return result;
    }
}
