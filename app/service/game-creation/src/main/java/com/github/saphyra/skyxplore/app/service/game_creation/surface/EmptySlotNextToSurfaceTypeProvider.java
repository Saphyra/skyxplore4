package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.saphyra.skyxplore.app.service.game_creation.surface.SlotUtil.isEmptySlot;

@Component
@RequiredArgsConstructor
@Slf4j
class EmptySlotNextToSurfaceTypeProvider {
    List<Coordinate> getEmptySlotsNextToSurfaceType(SurfaceType[][] surfaceMap, SurfaceType surfaceType) {
        Set<Coordinate> slotsNextToSurfaceType = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < surfaceMap.length; x++) {
            SurfaceType[] surfaceTypes = surfaceMap[x];
            stringBuilder.append("\n");
            for (int y = 0; y < surfaceTypes.length; y++) {
                stringBuilder.append(x).append(":").append(y).append(" - ").append(surfaceMap[x][y]).append(" ");
                if (surfaceMap[x][y] == surfaceType) {
                    slotsNextToSurfaceType.add(new Coordinate(x - 1, y));
                    slotsNextToSurfaceType.add(new Coordinate(x + 1, y));
                    slotsNextToSurfaceType.add(new Coordinate(x, y - 1));
                    slotsNextToSurfaceType.add(new Coordinate(x, y + 1));
                }
            }
        }
        log.debug("SurfaceMap: {}", stringBuilder);
        log.debug("All slots next to surfaceType {}: {}", surfaceType, slotsNextToSurfaceType);
        List<Coordinate> result = slotsNextToSurfaceType.stream()
            .filter(coordinate -> coordinate.getX() >= 0 && coordinate.getX() < surfaceMap.length)
            .filter(coordinate -> coordinate.getY() >= 0 && coordinate.getY() < surfaceMap.length)
            .filter(coordinate -> isEmptySlot(surfaceMap, coordinate))
            .collect(Collectors.toList());
        log.debug("Empty slots next to surfaceType {}: {}", surfaceType, slotsNextToSurfaceType);
        return result;
    }
}
