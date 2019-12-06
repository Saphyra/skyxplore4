package com.github.saphyra.skyxplore.data.gamedata.domain.terraforming;

import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Data;

@Data
public class TerraformingPossibility {
    private SurfaceType surfaceType;
    private ConstructionRequirements constructionRequirements;
}
