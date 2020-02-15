package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.terraforming;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import lombok.Data;

@Data
public class TerraformingPossibility {
    private SurfaceType surfaceType;
    private ConstructionRequirements constructionRequirements;
}
