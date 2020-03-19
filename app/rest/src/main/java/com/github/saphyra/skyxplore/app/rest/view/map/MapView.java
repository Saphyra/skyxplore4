package com.github.saphyra.skyxplore.app.rest.view.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class MapView {
    @NonNull
    private final List<StarMapView> stars;

    @NonNull
    private final List<StarConnectionView> connections;
}
