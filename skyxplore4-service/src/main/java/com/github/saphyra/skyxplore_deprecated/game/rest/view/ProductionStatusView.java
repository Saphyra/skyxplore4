package com.github.saphyra.skyxplore_deprecated.game.rest.view;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ProductionStatusView {
    @NonNull
    private final ConstructionStatus status;

    private final Integer currentWorkPoints;

    @NonNull
    private final Integer requiredWorkPoints;

    @NonNull
    private final Integer requiredResourcesAmount;

    @NonNull
    private final Integer allocatedResourcesAmount;
}
