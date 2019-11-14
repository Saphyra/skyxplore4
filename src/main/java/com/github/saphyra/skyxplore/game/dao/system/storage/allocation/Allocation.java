package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
//Used for existing resources, what will be used in a specific production
public class Allocation {
    @NonNull
    private final UUID allocationId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final String dataId;

    @NonNull
    private Integer amount;

    @NonNull
    private final AllocationType allocationType;
}
