package com.github.saphyra.skyxplore.game.map.surface.domain;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class Surface {
    @NonNull
    private final UUID surfaceId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final Coordinate coordinate;

    @NonNull
    private final SurfaceType surfaceType;

    private UUID buildingId;
}
