package com.github.saphyra.skyxplore.game.common.view;

import java.util.List;

import com.github.saphyra.skyxplore.game.map.connection.view.StarConnectionView;
import com.github.saphyra.skyxplore.game.map.star.view.StarView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Data
public class MapView {
    @NonNull
    private final List<StarView> stars;

    @NonNull
    private final List<StarConnectionView> connections;
}
