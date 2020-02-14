package com.github.saphyra.skyxplore.game.rest.view.queue;

import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore.game.rest.view.ProductionStatusView;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueueView extends ProductionStatusView {
    @NonNull
    private final QueueType queueType;

    @NonNull
    private final UUID queueItemId;

    @NonNull
    private final Integer priority;

    private final String additionalData;

    @NonNull
    private final OffsetDateTime addedAt;

    @Builder
    public QueueView(
        @NonNull ConstructionStatus status,
        Integer currentWorkPoints,
        @NonNull Integer requiredWorkPoints,
        @NonNull Integer requiredResourcesAmount,
        @NonNull Integer allocatedResourcesAmount,
        @NonNull QueueType queueType,
        @NonNull UUID queueItemId,
        @NonNull Integer priority,
        String additionalData,
        @NonNull OffsetDateTime addedAt
    ) {
        super(status, currentWorkPoints, requiredWorkPoints, requiredResourcesAmount, allocatedResourcesAmount);
        this.queueType = queueType;
        this.queueItemId = queueItemId;
        this.priority = priority;
        this.additionalData = additionalData;
        this.addedAt = addedAt;
    }
}
