package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.exceptionhandling.exception.InternalServerErrorException;
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
    private final ProducerQueryService producerQueryService;
    private final ResourceSpendService resourceSpendService;

    ProductionOrder getForOrder(ProductionOrder productionOrder) {
        ResourceSpendDetails spendDetails = resourceSpendService.getSpendDetails(productionOrder.getStarId(), productionOrder.getDataId(), productionOrder.getMissingAmount());
        resourceSpendService.spend(spendDetails);
        productionOrder.addProduced(spendDetails.getAvailableAmount());

        if (productionOrder.isReady()) {
            return productionOrder;
        }

        Producer producer = producerQueryService.getByStarIdAndDataId(productionOrder.getStarId(), productionOrder.getDataId())
            .stream()
            .min((o1, o2) -> -1 * o1.getLoad().compareTo(o2.getLoad()))
            .orElseThrow(() -> new InternalServerErrorException(String.format("Producer not found for ProductionOrder %s", productionOrder)));

        producer.produce(productionOrder);
        return productionOrder;
    }
}
