package com.github.saphyra.skyxplore.game.dao.common.coordinate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Coordinate {
    private final int x;
    private final int y;
}
