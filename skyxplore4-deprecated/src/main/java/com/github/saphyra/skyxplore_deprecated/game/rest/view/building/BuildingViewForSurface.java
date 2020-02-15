package com.github.saphyra.skyxplore_deprecated.game.rest.view.building;

import com.github.saphyra.skyxplore_deprecated.game.rest.view.ConstructionStatusView;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class BuildingViewForSurface {
    @NonNull
    private final UUID buildingId;

    @NonNull
    private final String dataId;

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer maxLevel;

    private final ConstructionStatusView construction;
}
