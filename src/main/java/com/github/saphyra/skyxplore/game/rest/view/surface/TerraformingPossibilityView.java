package com.github.saphyra.skyxplore.game.rest.view.surface;

import java.util.List;

import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class TerraformingPossibilityView {
    @NonNull
    private final SurfaceType surfaceType;

    private final List<String> researchRequirement;

    @NonNull
    private final ConstructionRequirements constructionRequirements;
}
