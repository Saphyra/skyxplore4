package com.github.saphyra.skyxplore.game.module.map.star.domain;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.domain.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
public class Star  {
    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final String starName;

    @NonNull
    private Coordinate coordinate;

    @NonNull
    private UUID ownerId;
}
