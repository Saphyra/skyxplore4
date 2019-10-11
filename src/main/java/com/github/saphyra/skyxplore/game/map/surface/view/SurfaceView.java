package com.github.saphyra.skyxplore.game.map.surface.view;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
    @NonNull
    private UUID buildingId;
}
