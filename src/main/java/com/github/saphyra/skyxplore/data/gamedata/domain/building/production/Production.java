package com.github.saphyra.skyxplore.data.gamedata.domain.building.production;

import java.util.List;
import java.util.Map;

import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.Data;

@Data
public class Production {
    private List<SurfaceType> placed;
    private Map<String, Integer> uses;
    private Integer amount;
}
