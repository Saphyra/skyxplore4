package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
@Slf4j
//TODO refactor - split
public class ProductionBuildingProducer implements Producer {
    @NonNull
    private final Building building;
    @NonNull
    private final ProductionBuilding productionBuilding;
    @NonNull
    private final ProductionContext productionContext;

    @Override
    public Double getLoad() {
        return productionContext.getProductionOrderQueryService()
            .getByProducerBuildingIdAndPlayerId(building.getBuildingId())
            .size() / (double) building.getLevel();
    }

    @Override
    public UUID getId() {
        return building.getBuildingId();
    }

    @Override
    public boolean produce(ProductionOrder order) {
        order.setProducerBuildingId(building.getBuildingId());

        int missingAmount = order.getTargetAmount() - order.getProducedAmount();
        Production production = productionBuilding.getGives().get(order.getDataId());

        List<String> existingResourceRequirements = order.getExistingResourceRequirements();
        Map<String, Integer> totalRequirements = production.getConstructionRequirements()
            .getRequiredResources()
            .entrySet()
            .stream()
            .filter(requirement -> !existingResourceRequirements.contains(requirement.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, requirement -> requirement.getValue() * missingAmount));

        List<ProductionOrder> existingOrders = productionContext.getProductionOrderQueryService().getByCustomerIdAndPlayerId(order.getCustomerId())
            .stream()
            .map(productionOrder -> productionContext.getProductionResourceProvider().getForOrder(productionOrder))
            .collect(Collectors.toList());

        List<ProductionOrder> requirementOrders = totalRequirements.entrySet()
            .stream()
            .map(requirement -> productionContext.getResourceProductionOrderFactory().createResourceOrder(order, requirement))
            .map(productionOrder -> productionContext.getProductionResourceProvider().getForOrder(productionOrder))
            .collect(Collectors.toList());

        List<ProductionOrder> processableOrders = Stream.concat(existingOrders.stream(), requirementOrders.stream())
            .collect(Collectors.toList());

        for (ProductionOrder productionOrder : processableOrders) {
            if (productionOrder.isReady()) {
                existingResourceRequirements.add(productionOrder.getDataId());
                productionContext.getProductionOrderCommandService().delete(productionOrder);
            } else {
                productionContext.getProductionOrderCommandService().save(productionOrder);
            }
        }

        if (existingResourceRequirements.containsAll(totalRequirements.keySet())) {
            log.info("ProductionOrder {} is finished.", order);
            //TODO finish
        }

        return false; //TODO calculate depletion
    }
}
