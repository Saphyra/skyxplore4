package com.github.saphyra.skyxplore.game.rest.view;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConstructionStatusView extends ProductionStatusView {
    @NonNull
    private final UUID constructionId;

    @Builder
    public ConstructionStatusView(
        @NonNull ConstructionStatus status,
        Integer currentWorkPoints,
        @NonNull Integer requiredWorkPoints,
        @NonNull Integer requiredResourcesAmount,
        @NonNull Integer allocatedResourcesAmount,
        @NonNull UUID constructionId
    ) {
        super(status, currentWorkPoints, requiredWorkPoints, requiredResourcesAmount, allocatedResourcesAmount);
        this.constructionId = constructionId;
    }
}
