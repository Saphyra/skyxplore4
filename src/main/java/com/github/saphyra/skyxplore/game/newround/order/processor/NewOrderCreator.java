package com.github.saphyra.skyxplore.game.newround.order.processor;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.order.StorageSettingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class NewOrderCreator {
    private static final int BATCH_SIZE = 100;

    private final StorageSettingProductionOrderFactory storageSettingProductionOrderFactory;

    List<ProductionOrder> createNewOrders(StorageSettingOrder settingOrder, List<ProductionOrder> existingOrders) {
        int orderRequiredAmount = getOrderRequiredAmount(settingOrder, existingOrders);

        List<ProductionOrder> newOrders = new ArrayList<>();
        for (int amount = orderRequiredAmount; amount > 0; amount -= BATCH_SIZE) {
            newOrders.add(storageSettingProductionOrderFactory.create(settingOrder.getStorageSetting(), Math.min(amount, BATCH_SIZE)));
        }
        return newOrders;
    }

    private int getOrderRequiredAmount(StorageSettingOrder order, List<ProductionOrder> existingOrders) {
        int missingAmount = order.getMissingAmount();
        int orderedAmount = getOrderedAmount(existingOrders);
        return missingAmount - orderedAmount;
    }

    private int getOrderedAmount(List<ProductionOrder> existingOrders) {
        return existingOrders.stream()
            .mapToInt(ProductionOrder::getTargetAmount)
            .sum();
    }
}
