package com.github.saphyra.skyxplore.game.rest.view.surface;

import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Builder
@Data
public class TerraformingPossibilityView {
    @NonNull
    private final SurfaceType surfaceType;

    private final String researchRequirement;

    @NonNull
    private final Map<String, Integer> resources;
}
