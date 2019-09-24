package com.github.saphyra.skyxplore.game.common.coordinates.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Coordinate {
    private int x;
    private int y;
}
