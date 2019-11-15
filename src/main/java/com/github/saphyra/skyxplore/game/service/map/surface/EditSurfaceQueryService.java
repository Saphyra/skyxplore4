package com.github.saphyra.skyxplore.game.service.map.surface;

import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.TerraformingPossibilitiesService;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibilities;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibility;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.rest.view.surface.BuildableBuildingView;
import com.github.saphyra.skyxplore.game.rest.view.surface.TerraformingPossibilityView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EditSurfaceQueryService {
    private final GameDataQueryService gameDataQueryService;
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
                .constructionRequirements(terraformingPossibility.getConstructionRequirements())
                .build();
    }

    public List<BuildableBuildingView> getBuildableBuildings(UUID surfaceId) {
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        return gameDataQueryService.getBuildingsBuildableAtSurfaceType(surface.getSurfaceType()).stream()
                .map(buildingData -> convert(surfaceId, buildingData))
                .collect(Collectors.toList());
    }

    private BuildableBuildingView convert(UUID surfaceId, BuildingData buildingData) {
        return BuildableBuildingView.builder()
                .dataId(buildingData.getId())
                .researchRequirement(null) //TODO implement
                .constructionRequirements(buildingData.getConstructionRequirements().get(1))
                .build();
    }
}
