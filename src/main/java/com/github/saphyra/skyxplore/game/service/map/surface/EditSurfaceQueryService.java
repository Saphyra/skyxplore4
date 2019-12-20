package com.github.saphyra.skyxplore.game.service.map.surface;

import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.data.gamedata.domain.terraforming.TerraformingPossibilities;
import com.github.saphyra.skyxplore.data.gamedata.domain.terraforming.TerraformingPossibilitiesService;
import com.github.saphyra.skyxplore.data.gamedata.domain.terraforming.TerraformingPossibility;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.star.Research;
import com.github.saphyra.skyxplore.game.dao.map.star.StarQueryService;
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
    private final StarQueryService starQueryService;
    private final SurfaceQueryService surfaceQueryService;
    private final TerraformingPossibilitiesService terraformingPossibilitiesService;

    public List<TerraformingPossibilityView> getTerraformingPossibilities(UUID surfaceId) {
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        List<String> researches = getResearches(surface);
        return terraformingPossibilitiesService.getOrDefault(surface.getSurfaceType(), new TerraformingPossibilities()).stream()
            .map(terraformingPossibility -> convert(terraformingPossibility, researches))
            .collect(Collectors.toList());
    }

    private TerraformingPossibilityView convert(TerraformingPossibility terraformingPossibility, List<String> researches) {
        return TerraformingPossibilityView.builder()
            .surfaceType(terraformingPossibility.getSurfaceType())
            .researchRequirement(terraformingPossibility.getConstructionRequirements().getResearchRequirements())
            .constructionRequirements(filterDevelopedResearches(researches, terraformingPossibility.getConstructionRequirements()))
            .build();
    }

    public List<BuildableBuildingView> getBuildableBuildings(UUID surfaceId) {
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        List<String> researches = getResearches(surface);
        return gameDataQueryService.getBuildingsBuildableAtSurfaceType(surface.getSurfaceType()).stream()
            .map(buildingData -> convert(buildingData, researches))
            .collect(Collectors.toList());
    }

    private List<String> getResearches(Surface surface) {
        return starQueryService.findByStarIdAndGameIdAndOwnerId(surface.getStarId())
            .getResearches()
            .stream()
            .map(Research::getDataId)
            .collect(Collectors.toList());
    }

    private BuildableBuildingView convert(BuildingData buildingData, List<String> researches) {
        return BuildableBuildingView.builder()
            .dataId(buildingData.getId())
            .constructionRequirements(aggregateConstructionRequirements(buildingData, researches))
            .build();
    }

    private ConstructionRequirements aggregateConstructionRequirements(BuildingData buildingData, List<String> researches) {
        ConstructionRequirements requirements = buildingData.getConstructionRequirements().get(1);
        return filterDevelopedResearches(researches, requirements);
    }

    private ConstructionRequirements filterDevelopedResearches(List<String> researches, ConstructionRequirements requirements) {
        return requirements.toBuilder()
            .researchRequirements(getMissingRequirements(requirements.getResearchRequirements(), researches))
            .build();
    }

    private List<String> getMissingRequirements(List<String> researchRequirements, List<String> researches) {
        return researchRequirements.stream()
            .filter(dataId -> !researches.contains(dataId))
            .collect(Collectors.toList());
    }
}
