package com.github.saphyra.skyxplore.game.module.system;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.module.system.building.domain.Building;
import com.github.saphyra.skyxplore.game.module.system.building.domain.BuildingDao;
import com.github.saphyra.skyxplore.game.module.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.module.system.storage.resource.domain.Resource;
import com.github.saphyra.skyxplore.game.module.system.storage.resource.domain.StorageType;
import com.github.saphyra.skyxplore.game.module.system.storage.resource.service.ResourceAverageCalculator;
import com.github.saphyra.skyxplore.game.module.system.storage.resource.service.ResourceDifferenceCalculator;
import com.github.saphyra.skyxplore.game.rest.view.system.ResourceDetailsView;
import com.github.saphyra.skyxplore.game.rest.view.system.StorageTypeView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageStatusQueryService {
    private final BuildingDao buildingDao;
    private final ResourceAverageCalculator resourceAverageCalculator;
    private final ResourceDifferenceCalculator resourceDifferenceCalculator;
    private final ResourceQueryService resourceQueryService;
    private final StorageBuildingService storageBuildingService;

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
                .reserved(0) //TODO fill when reservation is implemented
                .allocated(0) //TODO fill when allocation is implemented
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
                .reserved(0) //TODO calculate when reservation is implemented
                .difference(resourceDifferenceCalculator.getDifference(resource))
                .average(resourceAverageCalculator.getAverage(resource))
                .build();
    }
}
