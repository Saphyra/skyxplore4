package com.github.saphyra.skyxplore.game.service.system.storage;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageQueryService {
    private final BuildingDao buildingDao;
    private final ReservationDao reservationDao;
    private final ResourceDao resourceDao;
    private final StorageBuildingService storageBuildingService;

    public int getAvailableStoragePlace(UUID starId, StorageType storageType) {
        int capacity = getCapacity(starId, storageType);
        int usedStorage = getUsedStorage(starId, storageType);
        int reservedStorage = getReservedStorage(starId, storageType);
        int available = capacity - usedStorage - reservedStorage;
        log.info("Available storage for {} at system {}: {}. Capacity: {}, usedStorage: {}, reservedStorage: {}", storageType, starId, available, capacity, usedStorage, reservedStorage);
        return available;
    }


    private int getCapacity(UUID starId, StorageType storageType) {
        StorageBuilding buildingData = storageBuildingService.findByStorageType(storageType);
        return buildingDao.getByStarIdAndDataId(starId, buildingData.getId()).stream()
                .mapToInt(building -> building.getLevel() * buildingData.getCapacity())
                .sum();
    }

    private int getUsedStorage(UUID starId, StorageType storageType) {
        return resourceDao.getLatestByStarIdAndStorageType(starId, storageType).stream()
                .mapToInt(Resource::getAmount)
                .sum();
    }

    private int getReservedStorage(UUID starId, StorageType storageType) {
        return reservationDao.getByStarIdAndStorageType(starId, storageType).stream()
                .mapToInt(Reservation::getAmount)
                .sum();
    }

    public int getAvailableResource(UUID starId, String resourceId) {
        return resourceDao.findLatestByStarIdAndDataId(starId, resourceId)
                .map(Resource::getAmount)
                .orElse(0);
    }

    @AllArgsConstructor
    @Data
    private static class StorageDto<K> {
        @NonNull
        private final K key;

        @NonNull
        private final Integer amount;
    }
}
