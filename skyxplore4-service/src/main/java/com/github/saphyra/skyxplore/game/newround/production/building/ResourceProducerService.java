package com.github.saphyra.skyxplore.game.newround.production.building;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResource;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import com.github.saphyra.skyxplore.game.newround.hr.ProductionProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
class ResourceProducerService {
    private final HumanResourceService humanResourceService;
    private final ProductionBuildingService productionBuildingService;

    boolean produceResources(
        ProductionOrder order,
        Production production,
        ProductionBuildingProducer producer
    ) {
        log.info("ProductionOrder {} is finished.", order);
        Building building = producer.getBuilding();
        ProductionBuilding productionBuilding = productionBuildingService.get(building.getBuildingDataId());
        SkillType requiredSkill = productionBuilding.getGives().get(order.getDataId()).getRequiredSkill();
        int workPointsPerItem = production.getConstructionRequirements().getRequiredWorkPoints();

        boolean depleted;
        do {
            Optional<HumanResource> optionalHumanResource = humanResourceService.getOne(building.getGameId(), building.getStarId(), building.getBuildingId(), requiredSkill);
            if (optionalHumanResource.isPresent()) {
                HumanResource humanResource = optionalHumanResource.get();
                log.info("Available HumanResource found: {}", humanResource);
                depleted = allocateHumanResource(producer, building, productionBuilding, humanResource);

                if (!depleted) {
                    log.info("There is available HumanResource.");
                    produce(order, requiredSkill, workPointsPerItem, humanResource);
                }
            } else {
                log.info("Available HumanResource not found. Producer is depleted.");
                depleted = true;
            }
        } while (!order.isReady() && !depleted);
        return depleted;
    }

    private boolean allocateHumanResource(ProductionBuildingProducer producer, Building building, ProductionBuilding productionBuilding, HumanResource humanResource) {
        boolean humanResourceAvailable;
        if (humanResourceNotAllocated(humanResource)) {
            log.info("HumanResource is not allocated.");
            if (!buildingCanHireWorkers(producer, building, productionBuilding)) {
                log.info("HumanResource allocation failed - ProductionBuilding cannot employ more employees.");
                humanResourceAvailable = true;
            } else {
                log.info("Allocating HumanResource to Producer...");
                producer.allocateWorker();
                humanResource.setAllocation(building.getBuildingId());
                humanResourceAvailable = false;
            }
        } else {
            log.info("HumanResource is already allocated to the Producer.");
            humanResourceAvailable = false;
        }
        return humanResourceAvailable;
    }

    private boolean humanResourceNotAllocated(HumanResource humanResource) {
        return isNull(humanResource.getAllocation());
    }

    private boolean buildingCanHireWorkers(ProductionBuildingProducer producer, Building building, ProductionBuilding productionBuilding) {
        int maxWorkers = building.getLevel() * productionBuilding.getWorkers();
        boolean result = maxWorkers > producer.getAllocatedWorkers();
        log.info("Producer can hire workers: {}. maxWorkers: {}, allocatedWorkers: {}", result, maxWorkers, producer.getAllocatedWorkers());
        return result;
    }

    private void produce(ProductionOrder order, SkillType requiredSkill, int workPointsPerItem, HumanResource humanResource) {
        log.info("Production requested for order {}", order);
        ProductionProcess productionProcess = humanResource.produce(
            requiredSkill,
            order.getMissingAmount(),
            workPointsPerItem,
            order.getCurrentProgress()
        );
        order.addProduced(productionProcess.getFinishedProducts());
        order.setCurrentProgress(productionProcess.getCurrentProgress());
    }
}
