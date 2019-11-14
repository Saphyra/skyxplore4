package com.github.saphyra.skyxplore.game.rest.view.surface;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore.game.rest.view.building.BuildingViewForSurface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class SurfaceView {
    @NonNull
    private UUID surfaceId;
    @NonNull
    private Coordinate coordinate;
    @NonNull
    private SurfaceType surfaceType;
    private BuildingViewForSurface building;
}
