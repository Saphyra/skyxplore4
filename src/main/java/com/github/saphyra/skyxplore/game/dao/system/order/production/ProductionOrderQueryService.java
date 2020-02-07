package com.github.saphyra.skyxplore.game.dao.system.order.production;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductionOrderQueryService {
    private final ProductionOrderDao productionOrderDao;
    private final RequestContextHolder requestContextHolder;

    @Deprecated
    //TODO remove
    public List<ProductionOrder> getByOrderIdAndPlayerId(UUID orderId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return productionOrderDao.getByOrderIdAndPlayerId(orderId, playerId);
    }

    public List<ProductionOrder> getByProducerBuildingIdAndPlayerId(UUID productionBuildingId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return productionOrderDao.getByProducerBuildingIdAndPlayerId(productionBuildingId, playerId);
    }

    public List<ProductionOrder> getByCustomerIdAndPlayerId(UUID customerId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return productionOrderDao.getByCustomerIdAndPlayerId(customerId, playerId);
    }
}
