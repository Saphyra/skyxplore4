package com.github.saphyra.skyxplore.game.newround.hr;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class ProductionProcess {
    private final int finishedProducts;
    private final int currentProgress;
}
