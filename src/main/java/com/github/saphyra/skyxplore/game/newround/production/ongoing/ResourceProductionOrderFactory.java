package com.github.saphyra.skyxplore.game.newround.production.ongoing;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
class ResourceProductionOrderFactory {
    private final IdGenerator idGenerator;

    ProductionOrder createResourceOrder(ProductionOrder order, Map.Entry<String, Integer> requirement) {
        return ProductionOrder.builder()
            .productionOrderId(idGenerator.randomUUID())
            .gameId(order.getGameId())
            .starId(order.getStarId())
            .playerId(order.getPlayerId())
            .orderId(order.getOrderId())
            .customerId(order.getProductionOrderId())
            .dataId(requirement.getKey())
            .targetAmount(requirement.getValue())
            .isNew(true)
            .build();
    }

    ProductionOrder createResourceOrder(Construction order, Map.Entry<String, Integer> requirement) {
        return ProductionOrder.builder()
            .productionOrderId(idGenerator.randomUUID())
            .gameId(order.getGameId())
            .starId(order.getStarId())
            .playerId(order.getPlayerId())
            .orderId(order.getConstructionId())
            .customerId(order.getConstructionId())
            .dataId(requirement.getKey())
            .targetAmount(requirement.getValue())
            .isNew(true)
            .build();
    }
}
