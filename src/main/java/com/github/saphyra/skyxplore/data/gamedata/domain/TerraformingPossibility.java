package com.github.saphyra.skyxplore.data.gamedata.domain;

import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.Data;

import java.util.Map;

@Data
public class TerraformingPossibility {
    private SurfaceType surfaceType;
    private String researchRequirement;
    private Map<String, Integer> resources;
}
