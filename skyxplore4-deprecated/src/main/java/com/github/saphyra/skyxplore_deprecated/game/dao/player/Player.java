package com.github.saphyra.skyxplore_deprecated.game.dao.player;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Player {
    @NonNull
    private final UUID playerId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final String playerName;

    private final boolean ai;

    private final boolean isNew;
}
