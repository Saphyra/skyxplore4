package com.github.saphyra.skyxplore.game.module.map.connection.view;

import com.github.saphyra.skyxplore.game.common.domain.Coordinate;
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
