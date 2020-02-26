package com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

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

    List<ProductionOrder> getByProducerBuildingIdAndPlayerId(UUID productionBuildingId, UUID playerId) {
        return converter.convertEntity(repository.getByProducerBuildingIdAndPlayerId(
            uuidConverter.convertDomain(productionBuildingId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<ProductionOrder> getByCustomerIdAndPlayerId(UUID customerId, UUID playerId) {
        return converter.convertEntity(repository.getByCustomerIdAndPlayerId(
            uuidConverter.convertDomain(customerId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
