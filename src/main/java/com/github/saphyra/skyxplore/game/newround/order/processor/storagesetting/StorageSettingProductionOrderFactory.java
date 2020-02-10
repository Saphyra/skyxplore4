package com.github.saphyra.skyxplore.game.newround.order.processor.storagesetting;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StorageSettingProductionOrderFactory {
    private final IdGenerator idGenerator;

    ProductionOrder create(StorageSetting storageSetting, int targetAmount) {
        return ProductionOrder.builder()
            .productionOrderId(idGenerator.randomUUID())
            .gameId(storageSetting.getGameId())
            .starId(storageSetting.getStarId())
            .playerId(storageSetting.getPlayerId())
            .orderId(storageSetting.getStorageSettingId())
            .customerId(storageSetting.getStorageSettingId())
            .dataId(storageSetting.getDataId())
            .targetAmount(targetAmount)
            .producedAmount(0)
            .isNew(true)
            .build();
    }
}
