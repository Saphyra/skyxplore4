package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class Surface {
    @NonNull
    private final UUID surfaceId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final Coordinate coordinate;

    @NonNull
    private SurfaceType surfaceType;

    private final boolean isNew;
}
