package com.github.saphyra.skyxplore.game.service.system.storage;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceDao;
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
    private final AllocationDao allocationDao;
    private final BuildingQueryService buildingQueryService;
    private final ReservationDao reservationDao;
    private final ResourceDao resourceDao;
    private final StorageBuildingService storageBuildingService;

    int getAvailableStoragePlace(UUID starId, StorageType storageType) {
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
        return resourceDao.getLatestByStarIdAndStorageType(starId, storageType).stream()
            .mapToInt(Resource::getAmount)
            .sum();
    }

    public int getReservedStorage(UUID starId, StorageType storageType) {
        return getReservationsByStarIdAndStorageType(starId, storageType).stream()
            .mapToInt(Reservation::getAmount)
            .sum();
    }

    int getAvailableResource(UUID starId, String resourceId) {
        return resourceDao.findLatestByStarIdAndDataId(starId, resourceId)
            .map(Resource::getAmount)
            .orElse(0);
    }

    public Integer getAllocatedStorage(UUID starId, StorageType storageType) {
        return allocationDao.getByStarIdAndStorageType(starId, storageType).stream()
            .mapToInt(Allocation::getAmount)
            .sum();
    }

    public List<Allocation> getAllocationsByExternalReference(UUID externalReference) {
        return allocationDao.getByExternalReference(externalReference);
    }

    public Integer getReservationByStarIdAndDataId(UUID starId, String dataId) {
        return reservationDao.getByStarIdAndDataId(starId, dataId).stream()
            .mapToInt(Reservation::getAmount)
            .sum();
    }

    public Integer getAllocationByStarIdAndDataId(UUID starId, String dataId) {
        return allocationDao.getByStarIdAndDataId(starId, dataId).stream()
            .mapToInt(Allocation::getAmount)
            .sum();
    }

    public List<Reservation> getReservationsByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return reservationDao.getByStarIdAndStorageType(starId, storageType);
    }
}
