package com.github.saphyra.skyxplore.game.dao.system.construction;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class ConstructionResourceRequirement {
    @NonNull
    private final UUID constructionResourceRequirementId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID constructionId;

    @NonNull
    private final String resourceId;

    @NonNull
    private final Integer requiredAmount;
}
