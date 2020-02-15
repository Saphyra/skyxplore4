package com.github.saphyra.skyxplore_deprecated.game.rest.view.surface;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.ConstructionStatusView;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.building.BuildingViewForSurface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
    private ConstructionStatusView terraformStatus;
}
