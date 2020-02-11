package com.github.saphyra.skyxplore.game.newround.production.ongoing;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.resource.spend.ResourceSpendDetails;
import com.github.saphyra.skyxplore.game.newround.resource.spend.ResourceSpendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
class ProductionResourceProvider {
    private final ResourceSpendService resourceSpendService;

    ProductionOrder spendForOrder(ProductionOrder productionOrder) {
        ResourceSpendDetails spendDetails = resourceSpendService.getSpendDetails(productionOrder.getStarId(), productionOrder.getDataId(), productionOrder.getMissingAmount());
        resourceSpendService.spend(spendDetails);
        productionOrder.addProduced(spendDetails.getToSpend());
        return productionOrder;
    }
}
