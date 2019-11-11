package com.github.saphyra.skyxplore.game.common.domain;

import java.util.Map;

import lombok.Data;

@Data
public class ConstructionRequirements {
    private Integer workPoints;

    private Map<String, Integer> resources;
}
