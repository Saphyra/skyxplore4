package com.github.saphyra.skyxplore_deprecated.game.dao.system.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Building {
    @NonNull
    private final UUID buildingId;
    @NonNull
    private final String buildingDataId;
    @NonNull
    private final UUID gameId;
    @NonNull
    private final UUID starId;
    @NonNull
    private final UUID playerId;
    @NonNull
    private final UUID surfaceId;
    @NonNull
    private Integer level;
    private UUID constructionId;

    private final boolean isNew;

    public void increaseLevel() {
        level++;
    }
}
