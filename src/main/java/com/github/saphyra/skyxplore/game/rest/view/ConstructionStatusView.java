package com.github.saphyra.skyxplore.game.rest.view;

import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ConstructionStatusView {
    @NonNull
    private final UUID constructionId;

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
