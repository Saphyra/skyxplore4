package com.github.saphyra.skyxplore.game.newround.order.processor.terraform;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.order.TerraformOrder;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TerraformProductionOrderFactory {
    private final IdGenerator idGenerator;

    public List<ProductionOrder> createOrdersFor(TerraformOrder terraformOrder) {
        return getConstructionRequirements(terraformOrder.getConstruction())
            .entrySet()
            .stream()
            .map(resourceRequirement -> convert(resourceRequirement.getKey(), resourceRequirement.getValue(), terraformOrder.getConstruction()))
            .collect(Collectors.toList());
    }

    private ProductionOrder convert(String dataId, Integer amount, Construction construction) {
        return ProductionOrder.builder()
            .productionOrderId(idGenerator.randomUUID())
            .gameId(construction.getGameId())
            .starId(construction.getStarId())
            .playerId(construction.getPlayerId())
            .orderId(construction.getConstructionId())
            .customerId(construction.getConstructionId())
            .dataId(dataId)
            .targetAmount(amount)
            .isNew(true)
            .build();
    }

    private Map<String, Integer> getConstructionRequirements(Construction construction) {
        return construction.getConstructionRequirements()
            .getRequiredResources();
    }
}
