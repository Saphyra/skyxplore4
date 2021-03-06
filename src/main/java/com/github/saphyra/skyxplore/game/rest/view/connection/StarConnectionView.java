package com.github.saphyra.skyxplore.game.rest.view.connection;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StarConnectionView {
    private final Coordinate coordinate1;
    private final Coordinate coordinate2;
}
