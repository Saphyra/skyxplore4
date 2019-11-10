package com.github.saphyra.skyxplore.game.common.domain;

import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Data
public class ConstructionRequirements {
    @NonNull
    private final Integer workPoints;

    @NonNull
    private final Map<String, Integer> resources;
}
