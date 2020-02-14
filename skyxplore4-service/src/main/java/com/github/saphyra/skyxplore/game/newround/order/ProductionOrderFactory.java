package com.github.saphyra.skyxplore.game.newround.order;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductionOrderFactory {
    private final IdGenerator idGenerator;

    public ProductionOrder create(StorageSetting storageSetting, int targetAmount) {
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

    public ProductionOrder create(Construction construction, String dataId, int missingAmount) {
        return ProductionOrder.builder()
            .productionOrderId(idGenerator.randomUUID())
            .gameId(construction.getGameId())
            .starId(construction.getStarId())
            .playerId(construction.getPlayerId())
            .orderId(construction.getConstructionId())
            .customerId(construction.getConstructionId())
            .dataId(dataId)
            .targetAmount(missingAmount)
            .producedAmount(0)
            .isNew(true)
            .build();
    }
}
