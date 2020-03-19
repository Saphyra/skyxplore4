package com.github.saphyra.skyxplore.app.rest.view.map;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
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
