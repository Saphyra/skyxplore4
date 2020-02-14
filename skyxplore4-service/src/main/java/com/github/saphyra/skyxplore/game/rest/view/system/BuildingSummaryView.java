package com.github.saphyra.skyxplore.game.rest.view.system;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class BuildingSummaryView {
    @NonNull
    private final String dataId;

    @NonNull
    private final Integer usedSlots;

    @NonNull
    private final Integer levelSum;
}
