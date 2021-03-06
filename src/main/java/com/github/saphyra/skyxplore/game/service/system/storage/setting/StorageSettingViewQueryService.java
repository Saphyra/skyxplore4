package com.github.saphyra.skyxplore.game.service.system.storage.setting;

import com.github.saphyra.skyxplore.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore.game.dao.map.star.Research;
import com.github.saphyra.skyxplore.game.dao.map.star.ResearchQueryService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingCreationDetailsView;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingView;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageSettingViewQueryService {
    private final BuildingQueryService buildingQueryService;
    private final ProductionBuildingService productionBuildingService;
    private final ResearchQueryService researchQueryService;
    private final ResourceDataService resourceDataService;
    private final StorageSettingQueryService storageSettingQueryService;
    private final StorageQueryService storageQueryService;

    public List<StorageSettingView> getByStarIdAndPlayerId(UUID starId) {
        Map<StorageType, Integer> availableStorage = getAvailableStoragePlaces(starId);

        return storageSettingQueryService.getByStarIdAndPlayerId(starId)
            .stream()
            .map(storageSetting -> convertToView(storageSetting, availableStorage))
            .collect(Collectors.toList());
    }

    private StorageSettingView convertToView(StorageSetting storageSetting, Map<StorageType, Integer> availableStorage) {
        return StorageSettingView.builder()
            .storageSettingId(storageSetting.getStorageSettingId())
            .dataId(storageSetting.getDataId())
            .targetAmount(storageSetting.getTargetAmount())
            .priority(storageSetting.getPriority())
            .maxAmount(availableStorage.get(resourceDataService.get(storageSetting.getDataId()).getStorageType()))
            .batchSize(storageSetting.getBatchSize())
            .build();
    }

    public StorageSettingCreationDetailsView getStorageCreationDetails(UUID starId) {
        List<String> existingSettings = getExistingSettings(starId);

        Map<StorageType, Integer> availableStoragePlaces = getAvailableStoragePlaces(starId);

        List<ProductionBuilding> existingProductionBuildings = getExistingProductionBuildings(starId);
        List<Research> existingResearches = researchQueryService.getByStarIdAndPlayerId(starId);
        StorageSettingCreationDetailsView result = resourceDataService.values()
            .stream()
            .filter(storageBuilding -> !existingSettings.contains(storageBuilding.getId())) //Filter resources for existing settings
            .filter(resourceData -> producerAvailable(resourceData, existingProductionBuildings, existingResearches)) //Filter non-producible resources
            .filter(resourceData -> availableStoragePlaces.get(resourceData.getStorageType()) > 0) //Filter resources where storage is full
            .collect(Collectors.toMap(GameDataItem::getId, resourceData -> availableStoragePlaces.get(resourceData.getStorageType()), (integer, integer2) -> integer, StorageSettingCreationDetailsView::new));
        log.debug("StorageSettingCreationDetailsView: {}", result);
        return result;
    }

    private List<ProductionBuilding> getExistingProductionBuildings(UUID starId) {
        return buildingQueryService.getByStarIdAndPlayerId(starId)
            .stream()
            .filter(building -> productionBuildingService.containsKey(building.getBuildingDataId()))
            .map(building -> productionBuildingService.get(building.getBuildingDataId()))
            .collect(Collectors.toList());
    }

    private Map<StorageType, Integer> getAvailableStoragePlaces(UUID starId) {
        return Arrays.stream(StorageType.values())
            .collect(Collectors.toMap(Function.identity(), storageType -> storageQueryService.getAvailableStoragePlace(starId, storageType)));
    }

    private List<String> getExistingSettings(UUID starId) {
        return storageSettingQueryService.getByStarIdAndPlayerId(starId).stream()
            .map(StorageSetting::getDataId)
            .collect(Collectors.toList());
    }

    private boolean producerAvailable(ResourceData resourceData, List<ProductionBuilding> existingProductionBuildings, List<Research> existingResearches) {
        return existingProductionBuildings.stream()
            .filter(productionBuilding -> productionBuilding.getGives().containsKey(resourceData.getId()))
            .anyMatch(productionBuilding -> existingResearches.containsAll(productionBuilding.getGives()
                .get(resourceData.getId())
                .getConstructionRequirements()
                .getResearchRequirements())
            );
    }
}
