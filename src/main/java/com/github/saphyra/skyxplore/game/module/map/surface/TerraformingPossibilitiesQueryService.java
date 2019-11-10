package com.github.saphyra.skyxplore.game.module.map.surface;

import com.github.saphyra.skyxplore.data.gamedata.TerraformingPossibilitiesService;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibilities;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibility;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.Surface;
import com.github.saphyra.skyxplore.game.rest.view.surface.TerraformingPossibilityView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TerraformingPossibilitiesQueryService {
    private final SurfaceQueryService surfaceQueryService;
    private final TerraformingPossibilitiesService terraformingPossibilitiesService;

    public List<TerraformingPossibilityView> getTerraformingPossibilities(UUID surfaceId) {
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        return terraformingPossibilitiesService.getOrDefault(surface.getSurfaceType(), new TerraformingPossibilities()).stream()
                .map(terraformingPossibility -> convert(surfaceId, terraformingPossibility))
                .collect(Collectors.toList());
    }

    private TerraformingPossibilityView convert(UUID surfaceId, TerraformingPossibility terraformingPossibility) {
        return TerraformingPossibilityView.builder()
                .surfaceType(terraformingPossibility.getSurfaceType())
                .researchRequirement(null) //TODO implement
                .resources(terraformingPossibility.getResources())
                .build();
    }
}
