package com.github.saphyra.skyxplore.game.newround.production.building;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class OngoingProductionOrderQueryService {
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ProductionResourceProvider productionResourceProvider;
    private final ResourceProductionOrderFactory resourceProductionOrderFactory;

    List<ProductionOrder> getExistingOrders(ProductionOrder order) {
        return productionOrderQueryService.getByCustomerIdAndPlayerId(order.getProductionOrderId())
            .stream()
            .map(productionResourceProvider::spendForOrder)
            .collect(Collectors.toList());
    }

    List<ProductionOrder> getRequirementOrders(ProductionOrder order, Map<String, Integer> totalRequirements) {
        return totalRequirements.entrySet()
            .stream()
            .map(requirement -> resourceProductionOrderFactory.createResourceOrder(order, requirement))
            .map(productionResourceProvider::spendForOrder)
            .collect(Collectors.toList());
    }
}
