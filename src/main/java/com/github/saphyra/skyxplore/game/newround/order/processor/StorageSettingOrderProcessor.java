package com.github.saphyra.skyxplore.game.newround.order.processor;

import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.newround.order.StorageSettingOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageSettingOrderProcessor {
    private final BuildingQueryService buildingQueryService;

    public void process(StorageSettingOrder order) {
        StorageSetting storageSetting = order.getStorageSetting();
        log.info("Processing StorageSettingOrder for StorageSetting {} with priority {}", storageSetting.getStorageSettingId(), order.getPriority());

        List<Building> producers = buildingQueryService.getProducersByStarIdAndResourceDataId(storageSetting.getStarId(), storageSetting.getDataId());
        //TODO
    }
}
