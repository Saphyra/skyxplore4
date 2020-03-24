package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;

@UtilityClass
class SlotUtil {
    static boolean isEmptySlot(SurfaceType[][] surfaceMap, Coordinate coordinate) {
        return isNull(surfaceMap[coordinate.getX()][coordinate.getY()]);
    }
}
