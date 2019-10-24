package com.github.saphyra.skyxplore.game.map.surface.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum  SurfaceType {
    COAL_MINE,
    CONCRETE,
    DESERT,
    FOREST,
    LAKE,
    MOUNTAIN,
    OIL_FIELD,
    ORE_MINE,
    VOLCANO;

    @JsonCreator
    public static SurfaceType parse(String value) {
        return Arrays.stream(values())
            .filter(surfaceType -> surfaceType.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Value %s could not be parsed to SurfaceType", value)));
    }
}
