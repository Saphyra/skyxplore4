package com.github.saphyra.skyxplore.game.star.domain;

import com.github.saphyra.skyxplore.game.common.coordinates.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

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
}
