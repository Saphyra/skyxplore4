package com.github.saphyra.skyxplore.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore.common.OptionalHashMap;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.newround.order.ProductionOrderFactory;
import com.github.saphyra.util.OptionalMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductionOrderHandler {
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderFactory productionOrderFactory;
    private final ProductionOrderQueryService productionOrderQueryService;

    List<ProductionOrder> getProductionOrders(Construction construction, OptionalMap<String, Allocation> allocations) {
        OptionalMap<String, ProductionOrder> productionOrders = new OptionalHashMap<>(
            productionOrderQueryService.getByCustomerIdAndPlayerId(construction.getConstructionId())
                .stream()
                .collect(Collectors.toMap(ProductionOrder::getDataId, Function.identity()))
        );
        for (String dataId : allocations.keySet()) {
            int requiredAmount = construction.getConstructionRequirements().getRequiredResources().get(dataId);
            Allocation allocation = allocations.get(dataId);
            int missingAmount = requiredAmount - allocation.getAmount();
            Optional<ProductionOrder> optionalProductionOrder = productionOrders.getOptional(dataId);
            if (missingAmount > 0) {
                ProductionOrder productionOrder = optionalProductionOrder.orElseGet(() -> productionOrderFactory.create(construction, dataId, missingAmount));
                productionOrder.setTargetAmount(missingAmount);
                productionOrders.put(dataId, productionOrder);
            } else {
                optionalProductionOrder.ifPresent(order -> order.setTargetAmount(0));
            }
        }
        List<ProductionOrder> productionOrderList = new ArrayList<>(productionOrders.values());
        productionOrderCommandService.saveAll(productionOrderList);
        return new ArrayList<>(productionOrderList);
    }
}
