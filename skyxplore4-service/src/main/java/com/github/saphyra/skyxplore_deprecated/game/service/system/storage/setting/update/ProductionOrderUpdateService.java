package com.github.saphyra.skyxplore_deprecated.game.service.system.storage.setting.update;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductionOrderUpdateService {
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final StorageQueryService storageQueryService;

    void updateProductionOrderIfNecessary(UUID starId, String dataId, UUID storageSettingId, Integer targetAmount) {

        List<ProductionOrder> productionOrders = productionOrderQueryService.getByCustomerIdAndPlayerId(storageSettingId)
            .stream()
            .sorted((o1, o2) -> Integer.compare(o2.getMissingAmount(), o1.getMissingAmount()))
            .collect(Collectors.toList());
        int availableAmount = storageQueryService.getAvailableResourceAmountByStarIdAndDataIdAndPlayerId(starId, dataId);
        int orderedAmount = productionOrders.stream()
            .mapToInt(ProductionOrder::getTargetAmount)
            .sum();

        int surplus = orderedAmount + availableAmount - targetAmount;
        for (int index = 0; index < productionOrders.size() && surplus > 0; index++) {
            ProductionOrder productionOrder = productionOrders.get(index);
            int missingAmount = productionOrder.getMissingAmount();
            int reduction = Math.min(surplus, missingAmount);
            if (reduction > 0) {
                productionOrder.setTargetAmount(productionOrder.getTargetAmount() - reduction);
                productionOrderCommandService.save(productionOrder);
                surplus -= reduction;
            }
        }
    }
}
