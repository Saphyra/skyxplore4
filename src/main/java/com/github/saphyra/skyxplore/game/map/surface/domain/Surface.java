package com.github.saphyra.skyxplore.game.map.surface.domain;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import lombok.*;

import java.util.UUID;

import static java.util.Objects.isNull;

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
    private SurfaceType surfaceType;

    private UUID buildingId;

    public boolean isEmpty() {
        return isNull(buildingId);
    }
}
