package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Data
public class BuildingCache {
    @NonNull
    private final UUID buildingCacheId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final UUID buildingId;

    @NonNull
    private final String dataId;

    @NonNull
    private Integer amount;

    private final boolean isNew;
}
