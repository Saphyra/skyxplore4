package com.github.saphyra.skyxplore_deprecated.game.rest.view.system;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class PopulationView {
    @NonNull
    private final Integer dwellingSpaceAmount;

    @NonNull
    private final Integer citizenNum;
}
