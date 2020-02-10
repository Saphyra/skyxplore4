package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.skyxplore.game.common.interfaces.DisplayedQueueable;
import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Construction implements DisplayedQueueable {
    @NonNull
    private final UUID constructionId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final UUID surfaceId;

    @NonNull
    private final ConstructionRequirements constructionRequirements;

    @NonNull
    private final ConstructionType constructionType;

    @NonNull
    private ConstructionStatus constructionStatus;

    @NonNull
    private final UUID externalId;

    private Integer currentWorkPoints;

    @NonNull
    private Integer priority;

    private String additionalData;

    @Builder.Default
    private final List<String> existingResourceRequirements = new ArrayList<>();

    @NonNull
    private final OffsetDateTime addedAt;

    private final boolean isNew;

    @Override
    public QueueType getQueueType() {
        switch (constructionType) {
            case TERRAFORMING:
                return QueueType.TERRAFORMING;
            case BUILDING:
            case UPGRADE_BUILDING:
                return QueueType.CONSTRUCTION;
            default:
                throw new IllegalStateException(constructionType + " cannot be mapped to QueueType");
        }
    }

    @Override
    public UUID getId() {
        return constructionId;
    }

    @Override
    public OffsetDateTime addedAt() {
        return addedAt;
    }

    @Override
    public PriorityType getPriorityType() {
        return PriorityType.CONSTRUCTION;
    }

    public void addResource(String dataId) {
        existingResourceRequirements.add(dataId);
    }

    public boolean isResourcesAvailable() {
        return existingResourceRequirements.containsAll(constructionRequirements.getRequiredResources().keySet());
    }
}
