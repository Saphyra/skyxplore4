package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResource;
import com.github.saphyra.skyxplore.game.newround.hr.ProductionProcess;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

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

    private int allocatedWorkers = 0;

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

        Production production = productionBuilding.getGives().get(order.getDataId());

        List<String> existingResourceRequirements = order.getExistingResourceRequirements();
        Map<String, Integer> totalRequirements = getTotalRequirements(order.getMissingAmount(), production, existingResourceRequirements);
        List<ProductionOrder> existingOrders = getExistingOrders(order);

        List<ProductionOrder> requirementOrders = getRequirementOrders(order, totalRequirements);

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

        boolean depleted = false;
        if (existingResourceRequirements.containsAll(totalRequirements.keySet())) {
            log.info("ProductionOrder {} is finished.", order);
            do {
                ProductionBuilding productionBuilding = productionContext.getProductionBuildingService().get(building.getBuildingDataId());
                SkillType requiredSkill = productionBuilding.getGives().get(order.getDataId()).getRequiredSkill();
                Optional<HumanResource> optionalHumanResource = productionContext.getHumanResourceService().getOne(building.getGameId(), building.getStarId(), building.getBuildingId(), requiredSkill);
                if (optionalHumanResource.isPresent()) {
                    HumanResource humanResource = optionalHumanResource.get();
                    if (isNull(humanResource.getAllocation())) {
                        if (building.getLevel() * productionBuilding.getWorkers() == allocatedWorkers) {
                            depleted = true;
                            break;
                        } else {
                            allocatedWorkers++;
                            humanResource.setAllocation(building.getBuildingId());
                        }
                    }

                    int workPointsPerItem = production.getConstructionRequirements().getRequiredWorkPoints();
                    ProductionProcess productionProcess = humanResource.produce(
                        requiredSkill,
                        order.getMissingAmount(),
                        workPointsPerItem,
                        order.getCurrentProgress()
                    );
                    order.addProduced(productionProcess.getFinishedProducts());
                    order.setCurrentProgress(productionProcess.getCurrentProgress());
                } else {
                    depleted = true;
                }
            } while (order.isReady() || depleted);
        }

        return depleted;
    }

    private Map<String, Integer> getTotalRequirements(int missingAmount, Production production, List<String> existingResourceRequirements) {
        return production.getConstructionRequirements()
            .getRequiredResources()
            .entrySet()
            .stream()
            .filter(requirement -> !existingResourceRequirements.contains(requirement.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, requirement -> requirement.getValue() * missingAmount));
    }

    private List<ProductionOrder> getExistingOrders(ProductionOrder order) {
        return productionContext.getProductionOrderQueryService().getByCustomerIdAndPlayerId(order.getCustomerId())
            .stream()
            .map(productionOrder -> productionContext.getProductionResourceProvider().getForOrder(productionOrder))
            .collect(Collectors.toList());
    }

    private List<ProductionOrder> getRequirementOrders(ProductionOrder order, Map<String, Integer> totalRequirements) {
        return totalRequirements.entrySet()
            .stream()
            .map(requirement -> productionContext.getResourceProductionOrderFactory().createResourceOrder(order, requirement))
            .map(productionOrder -> productionContext.getProductionResourceProvider().getForOrder(productionOrder))
            .collect(Collectors.toList());
    }
}
