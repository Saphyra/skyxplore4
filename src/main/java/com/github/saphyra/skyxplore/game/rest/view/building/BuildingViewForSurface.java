package com.github.saphyra.skyxplore.game.rest.view.building;

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

    private Object construction;//TODO proper response
}
