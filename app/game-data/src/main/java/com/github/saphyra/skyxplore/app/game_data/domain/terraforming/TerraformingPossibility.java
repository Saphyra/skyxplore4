package com.github.saphyra.skyxplore.app.game_data.domain.terraforming;

import com.github.saphyra.skyxplore.app.domain.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import lombok.Data;

@Data
public class TerraformingPossibility {
    private SurfaceType surfaceType;
    private ConstructionRequirements constructionRequirements;
}
