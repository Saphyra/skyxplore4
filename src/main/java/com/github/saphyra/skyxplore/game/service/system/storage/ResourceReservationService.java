package com.github.saphyra.skyxplore.game.service.system.storage;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.ResourceData;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.system.storage.allocation.AllocationService;
import com.github.saphyra.skyxplore.game.service.system.storage.reservation.ReservationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceReservationService {
    private final AllocationService allocationService;
    private final GameDataQueryService gameDataQueryService;
    private final ReservationService reservationService;
    private final StorageQueryService storageQueryService;

    public void reserveResources(Surface surface, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> resourceEntry : resources.entrySet()) {
            reserveResource(surface, resourceEntry);
        }
    }

    private void reserveResource(Surface surface, Map.Entry<String, Integer> resourceEntry) {
        ResourceData resourceData = gameDataQueryService.getResourceData(resourceEntry.getKey());
        int availableStoragePlace = storageQueryService.getAvailableStoragePlace(surface.getStarId(), resourceData.getStorageType());
        int available = storageQueryService.getAvailableResource(surface.getStarId(), resourceEntry.getKey());

        int requiredResourceAmount = resourceEntry.getValue();
        if (available > 0) {
            int toAllocate = Math.min(available, requiredResourceAmount);
            allocationService.allocate(surface.getGameId(), surface.getUserId(), surface.getStarId(), resourceData, toAllocate, AllocationType.CONSTRUCTION);
            requiredResourceAmount -= toAllocate;
        }

        if (availableStoragePlace < requiredResourceAmount) {
            throw ExceptionFactory.storageFull(surface.getSurfaceId(), resourceData.getStorageType());
        }

        if (requiredResourceAmount > 0) {
            reservationService.reserve(
                surface.getGameId(),
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
