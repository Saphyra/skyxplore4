package com.github.saphyra.skyxplore.game.dao.system.order.production;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProductionOrderConverter extends ConverterBase<ProductionOrderEntity, ProductionOrder> {
    private final UuidConverter uuidConverter;

    @Override
    protected ProductionOrder processEntityConversion(ProductionOrderEntity entity) {
        return ProductionOrder.builder()
            .productionOrderId(uuidConverter.convertEntity(entity.getProductionOrderId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .orderId(uuidConverter.convertEntity(entity.getOrderId()))
            .customerId(uuidConverter.convertEntity(entity.getCustomerId()))
            .producerBuildingId(uuidConverter.convertEntity(entity.getProducerBuildingId()))
            .dataId(entity.getDataId())
            .targetAmount(entity.getTargetAmount())
            .producedAmount(entity.getProducedAmount())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected ProductionOrderEntity processDomainConversion(ProductionOrder domain) {
        return ProductionOrderEntity.builder()
            .productionOrderId(uuidConverter.convertDomain(domain.getProductionOrderId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .orderId(uuidConverter.convertDomain(domain.getOrderId()))
            .customerId(uuidConverter.convertDomain(domain.getCustomerId()))
            .producerBuildingId(uuidConverter.convertDomain(domain.getProducerBuildingId()))
            .dataId(domain.getDataId())
            .targetAmount(domain.getTargetAmount())
            .producedAmount(domain.getProducedAmount())
            .isNew(domain.isNew())
            .build();
    }
}
