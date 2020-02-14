package com.github.saphyra.skyxplore.game.service.system.storage;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.system.storage.allocation.AllocationFactory;
import com.github.saphyra.skyxplore.game.service.system.storage.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceReservationService {
    private final AllocationCommandService allocationCommandService;
    private final AllocationFactory allocationFactory;
    private final GameDataQueryService gameDataQueryService;
    private final ReservationService reservationService;
    private final StorageQueryService storageQueryService;
    private final SurfaceQueryService surfaceQueryService;

    public void reserveResources(UUID surfaceId, Map<String, Integer> resources, ReservationType reservationType, UUID externalReference) {
        reserveResources(
            surfaceQueryService.findBySurfaceIdAndPlayerId(surfaceId),
            resources,
            reservationType,
            externalReference
        );
    }

    public void reserveResources(Surface surface, Map<String, Integer> resources, ReservationType reservationType, UUID externalReference) {
        for (Map.Entry<String, Integer> resourceEntry : resources.entrySet()) {
            reserveResource(surface, resourceEntry, reservationType, externalReference);
        }
    }

    private void reserveResource(Surface surface, Map.Entry<String, Integer> resourceEntry, ReservationType reservationType, UUID externalReference) {
        reserveResource(surface.getGameId(), surface.getStarId(), surface.getPlayerId(), resourceEntry.getKey(), resourceEntry.getValue(), reservationType, externalReference);
    }

    private void reserveResource(UUID gameId, UUID starId, UUID playerId, String dataId, Integer amount, ReservationType reservationType, UUID externalReference) {
        ResourceData resourceData = gameDataQueryService.getResourceData(dataId);
        int availableStoragePlace = storageQueryService.getAvailableStoragePlace(starId, resourceData.getStorageType());
        int available = storageQueryService.getAvailableResourceAmountByStarIdAndDataIdAndPlayerId(starId, dataId);

        Optional<Allocation> allocation = Optional.empty();
        if (available > 0) {
            int toAllocate = Math.min(available, amount);
            allocation = Optional.of(allocationFactory.allocate(
                gameId,
                starId,
                externalReference,
                resourceData,
                toAllocate,
                AllocationType.CONSTRUCTION,
                playerId
            ));
            amount -= toAllocate;
        }

        if (availableStoragePlace < amount) {
            throw ExceptionFactory.storageFull(starId, resourceData.getStorageType());
        }

        allocation.ifPresent(allocationCommandService::save);

        if (amount > 0) {
            reservationService.reserve(
                gameId,
                starId,
                dataId,
                amount,
                resourceData.getStorageType(),
                reservationType,
                externalReference,
                playerId
            );
        }
    }
}
