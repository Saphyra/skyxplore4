package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameDataQueryService {
    private final List<AbstractDataService<String, ? extends BuildingData>> buildingDataServices;
    private final ResourceDataService resourceDataService;

    public List<BuildingData> getBuildingsBuildableAtSurfaceType(SurfaceType surfaceType) {
        return buildingDataServices.stream()
                .flatMap(buildingDataService -> buildingDataService.values().stream())
                .filter(buildingData -> buildingData.getPlaceableSurfaceTypes().contains(surfaceType))
                .collect(Collectors.toList());
    }

    public BuildingData findBuildingData(String dataId) {
        return buildingDataServices.stream()
                .flatMap(buildingDataService -> buildingDataService.values().stream())
                .filter(buildingData -> buildingData.getId().equals(dataId))
                .findFirst()
                .orElseThrow(() -> ExceptionFactory.dataNotFound(dataId));
    }

    public ResourceData getResourceData(String resourceDataId) {
        return resourceDataService.getOptional(resourceDataId)
                .orElseThrow(() -> ExceptionFactory.dataNotFound(resourceDataId));
    }
}
