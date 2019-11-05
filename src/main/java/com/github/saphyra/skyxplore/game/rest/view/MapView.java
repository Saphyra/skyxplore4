package com.github.saphyra.skyxplore.game.rest.view;

import java.util.List;

import com.github.saphyra.skyxplore.game.rest.view.connection.StarConnectionView;
import com.github.saphyra.skyxplore.game.rest.view.star.StarMapView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Data
public class MapView {
    @NonNull
    private final List<StarMapView> stars;

    @NonNull
    private final List<StarConnectionView> connections;
}