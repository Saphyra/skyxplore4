package com.github.saphyra.skyxplore.game.service.system.storage;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageQueryService {
    private final AllocationQueryService allocationQueryService;
    private final BuildingQueryService buildingQueryService;
    private final ReservationQueryService reservationQueryService;
    private final ResourceQueryService resourceQueryService;
    private final StorageBuildingService storageBuildingService;

    public int getAvailableStoragePlace(UUID starId, StorageType storageType) {
        int capacity = getCapacity(starId, storageType);
        int usedStorage = getUsedStorage(starId, storageType);
        int reservedStorage = getReservedStorage(starId, storageType);
        int available = capacity - usedStorage - reservedStorage;
        log.debug("Available storage for {} at system {}: {}. Capacity: {}, usedStorage: {}, reservedStorage: {}", storageType, starId, available, capacity, usedStorage, reservedStorage);
        return available;
    }


    private int getCapacity(UUID starId, StorageType storageType) {
        StorageBuilding buildingData = storageBuildingService.findByStorageType(storageType);
        return buildingQueryService.getByStarIdAndDataIdAndPlayerId(starId, buildingData.getId()).stream()
            .mapToInt(building -> building.getLevel() * buildingData.getCapacity())
            .sum();
    }

    private int getUsedStorage(UUID starId, StorageType storageType) {
        return resourceQueryService.getLatestByStarIdAndStorageType(starId, storageType).stream()
            .mapToInt(Resource::getAmount)
            .sum();
    }

    public int getReservedStorage(UUID starId, StorageType storageType) {
        return getReservationsByStarIdAndStorageType(starId, storageType).stream()
            .mapToInt(Reservation::getAmount)
            .sum();
    }

    public int getAvailableResourceAmountByStarIdAndDataIdAndPlayerId(UUID starId, String dataId) {
        return resourceQueryService.findLatestAmountByStarIdAndDataIdAndPlayerId(starId, dataId);
    }

    public Integer getAllocatedStorage(UUID starId, StorageType storageType) {
        return allocationQueryService.getByStarIdAndStorageTypeAndPlayerId(starId, storageType).stream()
            .mapToInt(Allocation::getAmount)
            .sum();
    }

    public List<Allocation> getAllocationsByExternalReference(UUID externalReference) {
        return allocationQueryService.getByExternalReferenceAndPlayerId(externalReference);
    }

    public Integer getReservationByStarIdAndDataId(UUID starId, String dataId) {
        return reservationQueryService.getByStarIdAndDataIdAndGameIdAndPlayerId(starId, dataId).stream()
            .mapToInt(Reservation::getAmount)
            .sum();
    }

    public Integer getAllocationByStarIdAndDataId(UUID starId, String dataId) {
        return allocationQueryService.getByStarIdAndDataIdAndPlayerId(starId, dataId).stream()
            .mapToInt(Allocation::getAmount)
            .sum();
    }

    public List<Reservation> getReservationsByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return reservationQueryService.getByStarIdAndStorageTypeAndPlayerId(starId, storageType);
    }
}
