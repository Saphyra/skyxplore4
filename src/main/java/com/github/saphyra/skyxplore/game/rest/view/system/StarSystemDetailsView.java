package com.github.saphyra.skyxplore.game.rest.view.system;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class StarSystemDetailsView {
    @NonNull
    private final List<StorageTypeView> storage;

    @NonNull
    private final PopulationView population;

    @NonNull
    private final List<SurfaceBuildingView> surfaceBuildings;

    //TODO System defence details
}
