package com.github.saphyra.skyxplore.app.rest.view.map;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

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
