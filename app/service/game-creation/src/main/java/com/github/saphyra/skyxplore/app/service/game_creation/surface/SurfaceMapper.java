package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class SurfaceMapper {
    private final SurfaceFactory surfaceFactory;

    List<Surface> mapSurfaces(SurfaceType[][] surfaceMap, Star star) {
        List<Surface> result = new ArrayList<>();
        for (int x = 0; x < surfaceMap.length; x++) {
            SurfaceType[] surfaceTypes = surfaceMap[x];
            for (int y = 0; y < surfaceTypes.length; y++) {
                Surface surface = surfaceFactory.create(
                    star.getStarId(),
                    star.getGameId(),
                    star.getOwnerId(),
                    new Coordinate(x, y),
                    surfaceMap[x][y]
                );
                result.add(surface);
            }
        }

        return result;
    }
}
