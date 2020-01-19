package com.github.saphyra.skyxplore.game.service.system.storage.setting.order.processor;

import com.github.saphyra.skyxplore.game.service.system.storage.setting.order.StorageSettingOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageSettingOrderProcessor {
    public void process(StorageSettingOrder order) {
        log.info("Processing StorageSettingOrder for StorageSetting {} with priority {}", order.getStorageSetting().getStorageSettingId(), order.getPriority());
        //TODO
    }
}
