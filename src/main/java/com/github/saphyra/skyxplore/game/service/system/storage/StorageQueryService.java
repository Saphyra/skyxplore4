package com.github.saphyra.skyxplore.game.service.system.storage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageQueryService {
    private final BuildingDao buildingDao;
    private final ReservationDao reservationDao;
    private final ResourceDao resourceDao;
    private final StorageBuildingService storageBuildingService;

    public Map<StorageType, Integer> getAvailableStoragePlaces(UUID starId) {
        return Arrays.stream(StorageType.values())
            .map(storageType -> getAvailableStoragePlace(starId, storageType))
            .collect(Collectors.toMap(StorageDto::getKey, StorageDto::getAmount));
    }

    private StorageDto<StorageType> getAvailableStoragePlace(UUID starId, StorageType storageType) {
        int capacity = getCapacity(starId, storageType);
        int usedStorage = getUsedStorage(starId, storageType);
        int reservedStorage = getReservedStorage(starId, storageType);
        int available = capacity - usedStorage - reservedStorage;
        log.info("Available storage for {} at system {}: {}. Capacity: {}, usedStorage: {}, reservedStorage: {}", storageType, starId, available, capacity, usedStorage, reservedStorage);
        return new StorageDto<>(storageType, available);
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

    public Map<String, Integer> getAvailableResources(UUID starId, Collection<String> resourceIds) {
        return resourceIds.stream()
            .map(dataId -> resourceDao.findLatestByStarIdAndDataId(starId, dataId)
                .map(resource -> new StorageDto<>(resource.getDataId(), resource.getAmount()))
                .orElse(new StorageDto<>(dataId, 0))
            )
            .collect(Collectors.toMap(StorageDto::getKey, StorageDto::getAmount));
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
