package com.github.saphyra.skyxplore.game.service.system.storage.setting;

import com.github.saphyra.skyxplore.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingCreationDetailsView;
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
public class StorageSettingCreationDetailsViewQueryService {
    private final ResourceDataService resourceDataService;
    private final StorageBuildingService storageBuildingService;
    private final StorageSettingQueryService storageSettingQueryService;
    private final StorageQueryService storageQueryService;

    public StorageSettingCreationDetailsView getStorageCreationDetails(UUID starId) {
        List<String> existingSettings = getExistingSettings(starId);

        Map<StorageType, Integer> availableStoragePlaces = getAvailableStoragePlaces(starId);

        StorageSettingCreationDetailsView result = resourceDataService.values()
            .stream()
            .filter(storageBuilding -> !existingSettings.contains(storageBuilding.getId()))
            .collect(Collectors.toMap(GameDataItem::getId, resourceData -> availableStoragePlaces.get(resourceData.getStorageType()), (integer, integer2) -> integer, StorageSettingCreationDetailsView::new));
        log.debug("StorageSettingCreationDetailsView: {}", result);
        return result;
    }

    private List<String> getExistingSettings(UUID starId) {
        return storageSettingQueryService.getByStarIdAndPlayerId(starId).stream()
            .map(StorageSetting::getDataId)
            .collect(Collectors.toList());
    }

    private Map<StorageType, Integer> getAvailableStoragePlaces(UUID starId) {
        return Arrays.stream(StorageType.values())
            .collect(Collectors.toMap(Function.identity(), storageType -> storageQueryService.getAvailableStoragePlace(starId, storageType)));
    }
}
