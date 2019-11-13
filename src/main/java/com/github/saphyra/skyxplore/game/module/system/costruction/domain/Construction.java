package com.github.saphyra.skyxplore.game.module.system.costruction.domain;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.domain.ConstructionRequirements;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Construction {
    @NonNull
    private final UUID constructionId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final ConstructionRequirements constructionRequirements;

    @NonNull
    private final ConstructionType constructionType;

    @NonNull
    private ConstructionStatus constructionStatus;

    private Integer currentWorkPoints;

    @NonNull
    private Integer priority;
}
