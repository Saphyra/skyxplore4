package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static java.util.Objects.isNull;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SlotUtil {
    static boolean isEmptySlot(SurfaceType[][] surfaceMap, Coordinate coordinate) {
        return isNull(surfaceMap[coordinate.getX()][coordinate.getY()]);
    }
}
