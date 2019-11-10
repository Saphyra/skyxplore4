package com.github.saphyra.skyxplore.data.gamedata.domain;

import com.github.saphyra.skyxplore.game.common.domain.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.Data;

@Data
public class TerraformingPossibility {
    private SurfaceType surfaceType;
    private String researchRequirement;
    private ConstructionRequirements constructionRequirements;
}
