package com.github.saphyra.skyxplore.game.service.system.storage.setting.order;

import com.github.saphyra.skyxplore.game.common.interfaces.Order;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.service.system.storage.setting.order.processor.StorageSettingOrderProcessor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class StorageSettingOrder implements Order {
    private final Integer priority;

    @NonNull
    private final StorageSetting storageSetting;

    @NonNull
    private final StorageSettingOrderProcessor orderProcessor;

    public void process() {
        orderProcessor.process(this);
    }
}
