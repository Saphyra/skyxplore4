package com.github.saphyra.skyxplore.game.service.system.building.build;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.ResourceData;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionService;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.allocation.AllocationService;
import com.github.saphyra.skyxplore.game.service.system.storage.reservation.ReservationService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildNewBuildingService {
    private final AllocationService allocationService;
    private final BuildingDao buildingDao;
    private final ConstructionService constructionService;
    private final GameDataQueryService gameDataQueryService;
    private final IdGenerator idGenerator;
    private final ReservationService reservationService;
    private final StorageQueryService storageQueryService;
    private final SurfaceQueryService surfaceQueryService;

    @Transactional
    public void buildNewBuilding(UUID gameId, UUID playerId, UUID surfaceId, String dataId) {
        BuildingData buildingData = gameDataQueryService.findBuildingData(dataId);
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        validateBuildingLocation(buildingData, surface);

        ConstructionRequirements constructionRequirements = buildingData.getConstructionRequirements().get(1);
        Map<String, Integer> resources = constructionRequirements.getResources();
        reserveResources(gameId, surfaceId, surface, resources);

        Building building = Building.builder()
                .buildingId(idGenerator.randomUUID())
                .buildingDataId(dataId)
                .gameId(gameId)
                .userId(surface.getUserId())
                .level(0)
                .constructionId(constructionService.create(gameId, surface.getUserId(), surface.getStarId(), constructionRequirements, ConstructionType.BUILDING))
                .build();
        buildingDao.save(building);
    }

    private void validateBuildingLocation(BuildingData buildingData, Surface surface) {
        if (!buildingData.getPlaceableSurfaceTypes().contains(surface.getSurfaceType())) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }

        if (!isNull(surface.getBuildingId())) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }
    }

    private void reserveResources(UUID gameId, UUID surfaceId, Surface surface, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> resourceEntry : resources.entrySet()) {
            reserveResource(gameId, surfaceId, surface, resourceEntry);
        }
    }

    private void reserveResource(UUID gameId, UUID surfaceId, Surface surface, Map.Entry<String, Integer> resourceEntry) {
        ResourceData resourceData = gameDataQueryService.getResourceData(resourceEntry.getKey());
        int availableStoragePlace = storageQueryService.getAvailableStoragePlace(surface.getStarId(), resourceData.getStorageType());
        int available = storageQueryService.getAvailableResource(surface.getStarId(), resourceEntry.getKey());

        int requiredResourceAmount = resourceEntry.getValue();
        if (available > 0) {
            int toAllocate = Math.min(available, requiredResourceAmount);
            allocationService.allocate(gameId, surface.getUserId(), surface.getStarId(), resourceEntry.getKey(), toAllocate, AllocationType.CONSTRUCTION);
            requiredResourceAmount -= toAllocate;
        }

        if (availableStoragePlace < requiredResourceAmount) {
            throw ExceptionFactory.storageFull(surfaceId, resourceData.getStorageType());
        }

        if (requiredResourceAmount > 0) {
            reservationService.reserve(
                    gameId,
                    surface.getUserId(),
                    surface.getStarId(),
                    resourceEntry.getKey(),
                    requiredResourceAmount,
                    resourceData.getStorageType(),
                    ReservationType.CONSTRUCTION
            );
        }
    }
}
