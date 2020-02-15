package com.github.saphyra.skyxplore_deprecated.game.rest.view;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
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
