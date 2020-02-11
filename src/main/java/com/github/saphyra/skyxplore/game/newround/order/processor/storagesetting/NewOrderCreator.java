package com.github.saphyra.skyxplore.game.newround.order.processor.storagesetting;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.order.StorageSettingOrder;
import com.github.saphyra.skyxplore.game.newround.order.ProductionOrderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class NewOrderCreator {
    private final ProductionOrderFactory productionOrderFactory;

    List<ProductionOrder> createNewOrders(StorageSettingOrder settingOrder, List<ProductionOrder> existingOrders) {
        int orderRequiredAmount = getOrderRequiredAmount(settingOrder, existingOrders);

        List<ProductionOrder> newOrders = new ArrayList<>();
        Integer batchSize = settingOrder.getStorageSetting().getBatchSize();
        for (int amount = orderRequiredAmount; amount > 0; amount -= batchSize) {
            newOrders.add(productionOrderFactory.create(settingOrder.getStorageSetting(), Math.min(amount, batchSize)));
        }
        log.info("Amount of new orders created: {}", newOrders.size());
        newOrders.forEach(productionOrder -> productionOrder.setNew(false));
        return newOrders;
    }

    private int getOrderRequiredAmount(StorageSettingOrder order, List<ProductionOrder> existingOrders) {
        int missingAmount = order.getMissingAmount();
        int orderedAmount = getOrderedAmount(existingOrders);
        int result = missingAmount - orderedAmount;
        log.info("Missing amount of {} : {}. Amount to produce: {}, already ordered: {}", order.getStorageSetting().getDataId(), result, missingAmount, orderedAmount);
        return result;
    }

    private int getOrderedAmount(List<ProductionOrder> existingOrders) {
        return existingOrders.stream()
            .mapToInt(ProductionOrder::getTargetAmount)
            .sum();
    }
}
