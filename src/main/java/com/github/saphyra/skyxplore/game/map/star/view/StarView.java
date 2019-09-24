package com.github.saphyra.skyxplore.game.map.star.view;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class StarView {
    @NonNull
    private final UUID starId;

    @NonNull
    private final String starName;

    @NonNull
    private final Coordinate coordinate;
}
