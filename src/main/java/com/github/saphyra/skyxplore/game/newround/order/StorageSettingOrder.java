package com.github.saphyra.skyxplore.game.newround.order;

import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.newround.order.processor.StorageSettingOrderProcessor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class StorageSettingOrder implements Order {
    @NonNull
    private final Integer priority;

    @NonNull
    private final Integer missingAmount;

    @NonNull
    private final StorageSetting storageSetting;

    @NonNull
    private final StorageSettingOrderProcessor orderProcessor;

    public void process() {
        orderProcessor.process(this);
    }
}
