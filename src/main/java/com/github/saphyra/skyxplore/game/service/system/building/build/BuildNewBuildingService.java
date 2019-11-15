package com.github.saphyra.skyxplore.game.service.system.building.build;

import static java.util.Objects.isNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.skyxplore.game.service.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildNewBuildingService {
    private final GameDataQueryService gameDataQueryService;
    private final StorageQueryService storageQueryService;
    private final SurfaceQueryService surfaceQueryService;

    @Transactional
    public void buildNewBuilding(UUID gameId, UUID playerId, UUID surfaceId, String dataId) {
        BuildingData buildingData = gameDataQueryService.findBuildingData(dataId);
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        validateBuildingLocation(buildingData, surface);

        Set<String> requiredResourceIds = buildingData.getConstructionRequirements().get(1).getResources().keySet();
        for(String resourceDataId : requiredResourceIds){
            //ResourceData resourceData = gameDataQueryService.getResourceData(resourceDataId); TODO implement
            Map<StorageType, Integer> availableStoragePlace = storageQueryService.getAvailableStoragePlaces(surface.getStarId());
            Map<String, Integer> availableResources = storageQueryService.getAvailableResources(surface.getStarId(), requiredResourceIds);
        }
    }

    private void validateBuildingLocation(BuildingData buildingData, Surface surface) {
        if (!buildingData.getPlaceableSurfaceTypes().contains(surface.getSurfaceType())) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }

        if (!isNull(surface.getBuildingId())) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }
    }
}
