package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class StorageSetting {
    @NonNull
    private final UUID storageSettingId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final String dataId;

    @NonNull
    private Integer targetAmount;

    @NonNull
    private Integer priority;
}
