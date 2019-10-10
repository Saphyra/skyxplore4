package com.github.saphyra.skyxplore.game.map.star.view;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
public class StarMapView {
    @NonNull
    private final UUID starId;

    @NonNull
    private final String starName;

    @NonNull
    private final Coordinate coordinate;

    @NonNull
    private final UUID ownerId;
}
