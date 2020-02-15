package com.github.saphyra.skyxplore_deprecated.game.newround.production.building;

import com.github.saphyra.exceptionhandling.exception.InternalServerErrorException;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.building.Building;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore_deprecated.game.newround.production.Producer;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

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

    private int allocatedWorkers;

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
        log.info("Producing ProductionOrder {}", order);
        boolean newOrder = isNull(order.getProducerBuildingId());
        Production production = productionBuilding.getGives().get(order.getDataId());
        Map<String, Integer> totalRequirements = getTotalRequirements(order.getMissingAmount(), production);
        log.info("newOrder: {}, totalRequirements: {}", newOrder, totalRequirements);

        List<ProductionOrder> requirementOrders = new ArrayList<>();
        if (newOrder) {
            order.setProducerBuildingId(building.getBuildingId());
            requirementOrders = context.getOngoingProductionOrderQueryService().getRequirementOrders(order, totalRequirements);
            log.info("requirementOrders: {}", requirementOrders);
        }

        List<ProductionOrder> existingOrders = context.getOngoingProductionOrderQueryService().getExistingOrders(order.getProductionOrderId());
        log.info("existingOrders: {}", existingOrders);

        List<ProductionOrder> processableOrders = Stream.concat(existingOrders.stream(), requirementOrders.stream())
            .filter(productionOrder -> !productionOrder.equals(order))
            .collect(Collectors.toList());

        List<String> existingResourceRequirements = order.getExistingResourceRequirements();
        for (ProductionOrder productionOrder : processableOrders) {
            processOrder(productionOrder);
            if (productionOrder.isReady()) {
                log.info("Requirement ProductionOrder is ready: {}", productionOrder);
                existingResourceRequirements.add(productionOrder.getDataId());
                context.getProductionOrderCommandService().delete(productionOrder);
            } else {
                log.info("Requirement ProductionOrder is not ready yet: {}", productionOrder);
                context.getProductionOrderCommandService().save(productionOrder);
            }
        }

        if (existingResourceRequirements.containsAll(totalRequirements.keySet())) {
            log.info("All resource requirements are present. Starting production on original order.");
            return context.getResourceProducerService().produceResources(order, production, this);
        } else {
            log.info("Not all resource requirements are present. existingResourceRequirements: {}, requirementList: {}", existingResourceRequirements, totalRequirements.keySet());
        }
        return false;
    }

    private Map<String, Integer> getTotalRequirements(int missingAmount, Production production) {
        return production.getConstructionRequirements()
            .getRequiredResources()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, requirement -> requirement.getValue() * missingAmount));
    }

    private void processOrder(ProductionOrder productionOrder) {
        log.info("Processing productionOrder as requirement: {}", productionOrder);
        Producer producer = context.getProducerQueryService().getByStarIdAndDataId(productionOrder.getStarId(), productionOrder.getDataId())
            .stream()
            .findFirst()
            .orElseThrow(() -> new InternalServerErrorException(String.format("Producer not found for ProductionOrder %s", productionOrder)));

        producer.produce(productionOrder);
    }

    void allocateWorker() {
        allocatedWorkers++;
    }
}
