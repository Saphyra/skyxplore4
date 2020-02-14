package com.github.saphyra.skyxplore.game.newround.production.ongoing;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OngoingProductionOrderQueryService {
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ProductionResourceProvider productionResourceProvider;
    private final ResourceProductionOrderFactory resourceProductionOrderFactory;

    public List<ProductionOrder> getExistingOrders(UUID customerId) {
        return productionOrderQueryService.getByCustomerIdAndPlayerId(customerId)
            .stream()
            .map(productionResourceProvider::spendForOrder)
            .collect(Collectors.toList());
    }

    public List<ProductionOrder> getRequirementOrders(ProductionOrder order, Map<String, Integer> totalRequirements) {
        return totalRequirements.entrySet()
            .stream()
            .map(requirement -> resourceProductionOrderFactory.createResourceOrder(order, requirement))
            .map(productionResourceProvider::spendForOrder)
            .collect(Collectors.toList());
    }
}
