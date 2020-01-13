package com.github.saphyra.skyxplore.game.rest.view.storage;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import org.springframework.stereotype.Component;

@Component
public class StorageSettingViewConverter implements ViewConverter<StorageSetting, StorageSettingView> {
    @Override
    public StorageSettingView convertDomain(StorageSetting domain) {
        return StorageSettingView.builder()
            .storageSettingId(domain.getStorageSettingId())
            .dataId(domain.getDataId())
            .targetAmount(domain.getTargetAmount())
            .priority(domain.getPriority())
            .build();
    }
}
