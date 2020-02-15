package com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
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
