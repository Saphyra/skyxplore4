package com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore_deprecated.game.newround.production.Producer;
import com.github.saphyra.skyxplore_deprecated.game.newround.production.ProducerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class ResourceProducer {
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProducerQueryService producerQueryService;

    void produceResources(List<ProductionOrder> productionOrders) {
        for (ProductionOrder productionOrder : productionOrders) {
            List<Producer> producers = producerQueryService.getByStarIdAndDataId(productionOrder.getStarId(), productionOrder.getDataId());
            for (int i = 0; i < producers.size() && !productionOrder.isReady(); i++) {
                producers.get(i).produce(productionOrder);
            }
        }
        productionOrderCommandService.saveAll(productionOrders);
    }
}
