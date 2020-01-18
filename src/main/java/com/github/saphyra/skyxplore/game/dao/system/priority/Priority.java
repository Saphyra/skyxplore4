package com.github.saphyra.skyxplore.game.dao.system.priority;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class Priority {
    @NonNull
    private final UUID starId;

    @NonNull
    private final PriorityType type;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private Integer value;

    private final boolean isNew;
}
