package com.github.saphyra.skyxplore.game.common.view.surface;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import com.github.saphyra.skyxplore.game.common.view.building.BuildingViewForSurface;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
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
