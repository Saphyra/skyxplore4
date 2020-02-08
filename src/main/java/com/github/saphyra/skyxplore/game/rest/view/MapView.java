package com.github.saphyra.skyxplore.game.rest.view;

import com.github.saphyra.skyxplore.game.rest.view.connection.StarConnectionView;
import com.github.saphyra.skyxplore.game.rest.view.star.StarMapView;
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
