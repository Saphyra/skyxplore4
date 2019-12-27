package com.github.saphyra.skyxplore.game.dao.map.connection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class StarConnection {
    @NonNull
    private final UUID connectionId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID star1;

    @NonNull
    private final UUID star2;

    private final boolean isNew;
}
