package com.github.saphyra.skyxplore.game.dao.map.star;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class Research {
    @NonNull
    private final UUID researchId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final String dataId;

    private final boolean isNew;
}
