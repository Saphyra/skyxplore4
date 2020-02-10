package com.github.saphyra.skyxplore.game.newround.order.processor.terraform;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore.game.newround.order.TerraformOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TerraformOrderProcessor {
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final TerraformProductionOrderFactory terraformProductionOrderFactory;

    public void process(TerraformOrder terraformOrder) {
        log.info("Processing terraformOrder {}", terraformOrder);

        Construction construction = terraformOrder.getConstruction();
        if (construction.getConstructionStatus() == ConstructionStatus.QUEUED) {
            List<ProductionOrder> productionOrders = terraformProductionOrderFactory.createOrdersFor(terraformOrder);
            productionOrderCommandService.saveAll(productionOrders);
            construction.setConstructionStatus(ConstructionStatus.RESOURCE_COLLECTION);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.RESOURCE_COLLECTION) {
            List<ProductionOrder> orders = productionOrderQueryService.getByCustomerIdAndPlayerId(construction.getConstructionId());
            //TODO implement
        }

        if (construction.getConstructionStatus() == ConstructionStatus.IN_PROGRESS) {
            //TODO work on production and complete when ready
        }
    }
}
