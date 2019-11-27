package com.github.saphyra.skyxplore.game.dao.system.construction;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.interfaces.DisplayedQueueable;
import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Construction implements DisplayedQueueable {
    @NonNull
    private final UUID constructionId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID starId;

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

    @NonNull
    private final OffsetDateTime addedAt;

    @Override
    public QueueType getQueueType() {
        return QueueType.CONSTRUCTION;
    }

    @Override
    public UUID getId() {
        return constructionId;
    }

    @Override
    public OffsetDateTime addedAt() {
        return addedAt;
    }
}
