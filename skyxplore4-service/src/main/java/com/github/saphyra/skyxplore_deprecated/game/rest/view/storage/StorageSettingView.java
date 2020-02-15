package com.github.saphyra.skyxplore_deprecated.game.rest.view.storage;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class StorageSettingView {
    @NonNull
    private final UUID storageSettingId;

    @NonNull
    private final String dataId;

    @NonNull
    private final Integer targetAmount;

    @NonNull
    private final Integer batchSize;

    @NonNull
    private final Integer priority;

    @NonNull
    private final Integer maxAmount;
}
