package com.github.saphyra.skyxplore.game.dao.system.order.production;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
class ProductionOrderDao extends AbstractDao<ProductionOrderEntity, ProductionOrder, String, ProductionOrderRepository> {
    private final UuidConverter uuidConverter;

    public ProductionOrderDao(ProductionOrderConverter converter, ProductionOrderRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<ProductionOrder> getByOrderIdAndPlayerId(UUID orderId, UUID playerId) {
        return converter.convertEntity(repository.getByOrderIdAndPlayerId(
            uuidConverter.convertDomain(orderId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
