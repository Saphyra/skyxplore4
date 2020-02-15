package com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.util.OptionalMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
class ConstructionResourceCollector {
    private final AllocationHandler allocationHandler;
    private final AllocationResolver allocationResolver;
    private final ConstructionCommandService constructionCommandService;
    private final ProductionOrderFinishService productionOrderFinishService;
    private final ProductionOrderHandler productionOrderHandler;
    private final ReservationUpdateService reservationUpdateService;
    private final ResourceProducer resourceProducer;

    void handleResourceCollection(Construction construction) {
        Map<String, Integer> requiredResources = construction.getConstructionRequirements().getRequiredResources();

        OptionalMap<String, Allocation> allocations = allocationHandler.getAllocations(construction, requiredResources);
        List<ProductionOrder> productionOrders = productionOrderHandler.getProductionOrders(construction, allocations);
        resourceProducer.produceResources(productionOrders);
        productionOrderFinishService.finishProductions(allocations, productionOrders);

        boolean allResourcePresent = reservationUpdateService.updateReservations(construction, requiredResources, allocations);
        if (allResourcePresent) {
            allocationResolver.resolveAllocations(allocations);
            construction.setConstructionStatus(ConstructionStatus.IN_PROGRESS);
        }

        constructionCommandService.save(construction);
    }
}
