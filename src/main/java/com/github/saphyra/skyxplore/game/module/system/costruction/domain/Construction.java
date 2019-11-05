package com.github.saphyra.skyxplore.game.module.system.costruction.domain;

import java.util.Map;
import java.util.UUID;

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
    private final Map<String, Integer> resourceRequirements;

    @NonNull
    private Integer priority;
}
