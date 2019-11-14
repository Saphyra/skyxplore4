package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class SurfaceBuildingView {
    @NonNull
    private final SurfaceType surfaceType;

    @NonNull
    private final Integer slots;

    @NonNull
    private final Integer usedSlots;

    @NonNull
    private final List<BuildingSummaryView> buildingSummary;
}
