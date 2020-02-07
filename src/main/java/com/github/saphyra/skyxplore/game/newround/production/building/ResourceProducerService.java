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
            //TODO add logging
            Optional<HumanResource> optionalHumanResource = humanResourceService.getOne(building.getGameId(), building.getStarId(), building.getBuildingId(), requiredSkill);
            if (optionalHumanResource.isPresent()) {
                HumanResource humanResource = optionalHumanResource.get();
                depleted = allocateHumanResource(producer, building, productionBuilding, humanResource);

                if (!depleted) {
                    produce(order, requiredSkill, workPointsPerItem, humanResource);
                }
            } else {
                depleted = true;
            }
        } while (!order.isReady() || !depleted);
        return depleted;
    }

    private boolean allocateHumanResource(ProductionBuildingProducer producer, Building building, ProductionBuilding productionBuilding, HumanResource humanResource) {
        if (humanResourceNotAllocated(humanResource)) {
            if (!buildingCanHireWorkers(producer, building, productionBuilding)) {
                return true;
            } else {
                producer.allocateWorker();
                humanResource.setAllocation(building.getBuildingId());
                return false;
            }
        }
        return false;
    }

    private boolean humanResourceNotAllocated(HumanResource humanResource) {
        return isNull(humanResource.getAllocation());
    }

    private boolean buildingCanHireWorkers(ProductionBuildingProducer producer, Building building, ProductionBuilding productionBuilding) {
        return building.getLevel() * productionBuilding.getWorkers() == producer.getAllocatedWorkers();
    }

    private void produce(ProductionOrder order, SkillType requiredSkill, int workPointsPerItem, HumanResource humanResource) {
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
