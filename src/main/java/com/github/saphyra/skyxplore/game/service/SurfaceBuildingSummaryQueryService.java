package com.github.saphyra.skyxplore.game.service;

import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.rest.view.system.BuildingSummaryView;
import com.github.saphyra.skyxplore.game.rest.view.system.SurfaceBuildingView;
import com.github.saphyra.skyxplore.game.service.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.service.system.building.BuildingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
            .map(surface -> buildingQueryService.findBySurfaceId(surface.getSurfaceId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        return SurfaceBuildingView.builder()
            .surfaceType(entry.getKey())
            .slots(entry.getValue().size())
            .usedSlots(buildings.size())
            .buildingSummary(summarize(buildings))
            .build();
    }

    private List<BuildingSummaryView> summarize(List<Building> buildings) {
        Map<String, List<Building>> buildingMap = buildings.stream()
            .collect(Collectors.groupingBy(Building::getBuildingDataId));

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
