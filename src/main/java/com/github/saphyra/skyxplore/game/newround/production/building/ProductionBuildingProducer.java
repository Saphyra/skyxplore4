package com.github.saphyra.skyxplore.game.newround.production.building;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.production.Producer;
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
public class ProductionBuildingProducer implements Producer {
    @NonNull
    private final Building building;
    @NonNull
    private final ProductionBuilding productionBuilding;
    @NonNull
    private final ProductionBuildingProducerContext context;

    private int allocatedWorkers = 0;

    @Override
    public Double getLoad() {
        return context.getProductionOrderQueryService()
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

        Production production = productionBuilding.getGives().get(order.getDataId());

        List<String> existingResourceRequirements = order.getExistingResourceRequirements();
        Map<String, Integer> totalRequirements = getTotalRequirements(order.getMissingAmount(), production, existingResourceRequirements);
        List<ProductionOrder> existingOrders = context.getOngoingProductionOrderQueryService().getExistingOrders(order);

        List<ProductionOrder> requirementOrders = context.getOngoingProductionOrderQueryService().getRequirementOrders(order, totalRequirements);

        List<ProductionOrder> processableOrders = Stream.concat(existingOrders.stream(), requirementOrders.stream())
            .collect(Collectors.toList());

        for (ProductionOrder productionOrder : processableOrders) {
            if (productionOrder.isReady()) {
                existingResourceRequirements.add(productionOrder.getDataId());
                context.getProductionOrderCommandService().delete(productionOrder);
            } else {
                context.getProductionOrderCommandService().save(productionOrder);
            }
        }

        if (existingResourceRequirements.containsAll(totalRequirements.keySet())) {
            return context.getResourceProducerService().produceResources(order, production, this);
        }
        return false;
    }

    private Map<String, Integer> getTotalRequirements(int missingAmount, Production production, List<String> existingResourceRequirements) {
        return production.getConstructionRequirements()
            .getRequiredResources()
            .entrySet()
            .stream()
            .filter(requirement -> !existingResourceRequirements.contains(requirement.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, requirement -> requirement.getValue() * missingAmount));
    }

    public void allocateWorker() {
        allocatedWorkers++;
    }
}
