package com.github.saphyra.skyxplore.game.module;

import com.github.saphyra.skyxplore.game.module.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.Surface;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import com.github.saphyra.skyxplore.game.module.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.module.system.building.domain.Building;
import com.github.saphyra.skyxplore.game.rest.view.system.BuildingSummaryView;
import com.github.saphyra.skyxplore.game.rest.view.system.SurfaceBuildingView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class SurfaceBuildingSummaryQueryService {
    private final BuildingQueryService buildingQueryService;
    private final SurfaceQueryService surfaceQueryService;

    public List<SurfaceBuildingView> getSummary(UUID starId) {
        Map<SurfaceType, List<Surface>> surfaceMap = surfaceQueryService.getMappingBySurfaceType(starId);
        return surfaceMap.entrySet().stream()
                .map(this::summarize)
                .collect(Collectors.toList());
    }

    private SurfaceBuildingView summarize(Map.Entry<SurfaceType, List<Surface>> entry) {
        List<Building> buildings = entry.getValue().stream()
                .filter(surface -> !isNull(surface.getBuildingId()))
                .map(surface -> buildingQueryService.findOneValidated(surface.getBuildingId()))
                .collect(Collectors.toList());

        return SurfaceBuildingView.builder()
                .surfaceType(entry.getKey())
                .slots(entry.getValue().size())
                .usedSlots(buildings.size())
                .buildingSummary(summarize(buildings))
                .build();
    }

    private List<BuildingSummaryView> summarize(List<Building> buildings) {


        Map<String, List<Building>> buildingMap = new HashMap<>();
        for (Building building : buildings) {
            if (!buildingMap.containsKey(building.getBuildingDataId())) {
                buildingMap.put(building.getBuildingDataId(), new ArrayList<>());
            }
            buildingMap.get(building.getBuildingDataId()).add(building);
        }

        return mapBuildings(buildingMap);
    }

    private List<BuildingSummaryView> mapBuildings(Map<String, List<Building>> buildingMap) {
        return buildingMap.entrySet().stream()
                .map(entry -> BuildingSummaryView.builder()
                        .dataId(entry.getKey())
                        .usedSlots(entry.getValue().size())
                        .levelSum(countLevel(entry.getValue()))
                        .build())
                .collect(Collectors.toList());
    }

    private Integer countLevel(List<Building> buildings) {
        return buildings.stream()
                .mapToInt(Building::getLevel)
                .sum();
    }
}
