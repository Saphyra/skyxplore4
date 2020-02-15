package com.github.saphyra.skyxplore_deprecated.game.service.system;

import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.building.Building;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.ResourceDetailsView;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.StorageTypeView;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.StorageQueryService;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.resource.ActualResourceQueryService;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.resource.ResourceAverageCalculator;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.resource.ResourceDifferenceCalculator;
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
    private final BuildingQueryService buildingQueryService;
    private final ResourceAverageCalculator resourceAverageCalculator;
    private final ResourceDifferenceCalculator resourceDifferenceCalculator;
    private final ActualResourceQueryService resourceQueryService;
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
        List<Building> buildings = buildingQueryService.getByStarIdAndDataIdAndPlayerId(starId, storage.getId());

        List<Resource> resources = resourceQueryService.getActualsByStarIdAndStorageType(starId, storageType);
        List<String> dataIds = resources.stream()
            .map(Resource::getDataId)
            .collect(Collectors.toList());
        List<ResourceDetailsView> resourceDetailsViews = mapResources(resources);
        resourceDetailsViews.addAll(createReservedOnlyResourceDetails(starId, storageType, dataIds));
        return StorageTypeView.builder()
            .capacity(countCapacity(storage, buildings))
            .actual(countResources(resources))
            .storageType(storageType)
            .reserved(storageQueryService.getReservedStorage(starId, storageType))
            .allocated(storageQueryService.getAllocatedStorage(starId, storageType))
            .resources(resourceDetailsViews)
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

    private List<ResourceDetailsView> createReservedOnlyResourceDetails(UUID starId, StorageType storageType, List<String> dataIds) {
        return storageQueryService.getReservationsByStarIdAndStorageType(starId, storageType).stream()
            .filter(reservation -> !dataIds.contains(reservation.getDataId()))
            .collect(Collectors.groupingBy(Reservation::getDataId))
            .entrySet().stream()
            .map(entry -> ResourceDetailsView.builder()
                .dataId(entry.getKey())
                .amount(0)
                .allocated(0)
                .reserved(entry.getValue().stream().mapToInt(Reservation::getAmount).sum())
                .difference(0)
                .average(0)
                .build())
            .collect(Collectors.toList());


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
            .allocated(storageQueryService.getAllocationByStarIdAndDataId(resource.getStarId(), resource.getDataId()))
            .reserved(storageQueryService.getReservationByStarIdAndDataId(resource.getStarId(), resource.getDataId()))
            .difference(resourceDifferenceCalculator.getDifference(resource))
            .average(resourceAverageCalculator.getAverage(resource))
            .build();
    }
}
