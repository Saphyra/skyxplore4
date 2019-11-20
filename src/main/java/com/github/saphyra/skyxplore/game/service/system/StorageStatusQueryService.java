package com.github.saphyra.skyxplore.game.service.system;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.skyxplore.game.rest.view.system.ResourceDetailsView;
import com.github.saphyra.skyxplore.game.rest.view.system.StorageTypeView;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.resource.ResourceAverageCalculator;
import com.github.saphyra.skyxplore.game.service.system.storage.resource.ResourceDifferenceCalculator;
import com.github.saphyra.skyxplore.game.service.system.storage.resource.ResourceQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageStatusQueryService {
    private final BuildingDao buildingDao;
    private final ResourceAverageCalculator resourceAverageCalculator;
    private final ResourceDifferenceCalculator resourceDifferenceCalculator;
    private final ResourceQueryService resourceQueryService;
    private final StorageBuildingService storageBuildingService;
    private final StorageQueryService storageQueryService;

    public List<StorageTypeView> getStorageStatusOfStar(UUID starId) {
        return Arrays.stream(StorageType.values())
                .filter(storageType -> storageType != StorageType.CITIZEN)
                .map(storageType -> summarize(storageType, starId))
                .collect(Collectors.toList());
    }

    private StorageTypeView summarize(StorageType storageType, UUID starId) {
        StorageBuilding storage = storageBuildingService.findByStorageType(storageType);
        List<Building> buildings = buildingDao.getByStarIdAndDataId(starId, storage.getId());

        List<Resource> resources = resourceQueryService.getActualsByStarIdAndStorageType(starId, storageType);
        return StorageTypeView.builder()
                .capacity(countCapacity(storage, buildings))
                .actual(countResources(resources))
                .storageType(storageType)
                .reserved(storageQueryService.getReservedStorage(starId, storageType))
                .allocated(storageQueryService.getAllocatedStorage(starId, storageType))
                .resources(mapResources(resources))
                .build();
    }

    private Integer countCapacity(StorageBuilding storageData, List<Building> buildings) {
        return buildings.stream()
                .mapToInt(building -> storageData.getCapacity() * building.getLevel())
                .sum();
    }

    private Integer countResources(List<Resource> resources) {
        return resources.stream()
                .mapToInt(Resource::getAmount)
                .sum();
    }

    private List<ResourceDetailsView> mapResources(List<Resource> resources) {
        return resources.stream()
                .map(this::mapResource)
                .collect(Collectors.toList());
    }

    private ResourceDetailsView mapResource(Resource resource) {
        return ResourceDetailsView.builder()
                .dataId(resource.getDataId())
                .amount(resource.getAmount())
                .reserved(storageQueryService.getReservationByStarIdAndDataId(resource.getStarId(), resource.getDataId()))
                .difference(resourceDifferenceCalculator.getDifference(resource))
                .average(resourceAverageCalculator.getAverage(resource))
                .build();
    }
}
