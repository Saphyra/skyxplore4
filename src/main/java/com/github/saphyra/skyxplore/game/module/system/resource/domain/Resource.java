package com.github.saphyra.skyxplore.game.module.system.resource.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class Resource {
    @NonNull
    private final UUID resourceId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final StorageType storageType;

    @NonNull
    private final String dataId;

    @NonNull
    private final UUID starId;

    @NonNull
    private Integer amount;

    @NonNull
    private final Integer round;
}
