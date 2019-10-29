package com.github.saphyra.skyxplore.game.rest.view.system;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ResourceDetailsView {
    @NonNull
    private final String dataId;

    @NonNull
    private final Integer amount;

    @NonNull
    private final Integer difference;

    @NonNull
    private final Integer average;
}
