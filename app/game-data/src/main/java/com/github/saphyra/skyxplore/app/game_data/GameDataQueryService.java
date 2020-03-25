package com.github.saphyra.skyxplore.app.game_data;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.data.AbstractDataService;
import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.game_data.domain.building.BuildingData;
import com.github.saphyra.skyxplore.app.game_data.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.app.game_data.domain.resource.ResourceDataService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//TODO unit test
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
