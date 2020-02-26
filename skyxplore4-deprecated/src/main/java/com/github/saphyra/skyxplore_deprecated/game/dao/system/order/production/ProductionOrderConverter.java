package com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.util.ObjectMapperWrapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ProductionOrderConverter extends ConverterBase<ProductionOrderEntity, ProductionOrder> {
    private final ObjectMapperWrapper objectMapperWrapper;
    private final UuidConverter uuidConverter;

    @Override
    protected ProductionOrder processEntityConversion(ProductionOrderEntity entity) {
        return ProductionOrder.builder()
            .productionOrderId(uuidConverter.convertEntity(entity.getProductionOrderId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .orderId(uuidConverter.convertEntity(entity.getOrderId()))
            .customerId(uuidConverter.convertEntity(entity.getCustomerId()))
            .producerBuildingId(uuidConverter.convertEntity(entity.getProducerBuildingId()))
            .dataId(entity.getDataId())
            .targetAmount(entity.getTargetAmount())
            .producedAmount(entity.getProducedAmount())
            .currentProgress(entity.getCurrentProgress())
            .isNew(false)
            .existingResourceRequirements(objectMapperWrapper.readArrayValue(entity.getExistingResourceRequirements(), String[].class))
            .build();
    }

    @Override
    protected ProductionOrderEntity processDomainConversion(ProductionOrder domain) {
        return ProductionOrderEntity.builder()
            .productionOrderId(uuidConverter.convertDomain(domain.getProductionOrderId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .orderId(uuidConverter.convertDomain(domain.getOrderId()))
            .customerId(uuidConverter.convertDomain(domain.getCustomerId()))
            .producerBuildingId(uuidConverter.convertDomain(domain.getProducerBuildingId()))
            .dataId(domain.getDataId())
            .targetAmount(domain.getTargetAmount())
            .producedAmount(domain.getProducedAmount())
            .currentProgress(domain.getCurrentProgress())
            .isNew(domain.isNew())
            .existingResourceRequirements(objectMapperWrapper.writeValueAsString(domain.getExistingResourceRequirements()))
            .build();
    }
}
