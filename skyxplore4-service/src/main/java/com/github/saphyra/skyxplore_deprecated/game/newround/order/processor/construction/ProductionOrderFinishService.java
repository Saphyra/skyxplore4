package com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.ResourceCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.ResourceQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductionOrderFinishService {
    private final AllocationCommandService allocationCommandService;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ResourceCommandService resourceCommandService;
    private final ResourceQueryService resourceQueryService;

    void finishProductions(Map<String, Allocation> allocations, List<ProductionOrder> productionOrders) {
        for (ProductionOrder productionOrder : productionOrders) {
            if (productionOrder.isReady()) {
                int producedAmount = productionOrder.getTargetAmount();

                Resource resource = resourceQueryService.findByStarIdAndDataIdAndRoundAndPlayerIdValidated(productionOrder.getStarId(), productionOrder.getDataId());
                resource.addAmount(producedAmount);
                resourceCommandService.save(resource);

                Allocation allocation = allocations.get(productionOrder.getDataId());
                allocation.addAmount(producedAmount);
                allocationCommandService.save(allocation);

                productionOrderCommandService.delete(productionOrder);
            }
        }
    }
}
