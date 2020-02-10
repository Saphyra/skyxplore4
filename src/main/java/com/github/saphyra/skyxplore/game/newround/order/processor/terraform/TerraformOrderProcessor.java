package com.github.saphyra.skyxplore.game.newround.order.processor.terraform;

import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceCommandService;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResource;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import com.github.saphyra.skyxplore.game.newround.hr.ProductionProcess;
import com.github.saphyra.skyxplore.game.newround.order.TerraformOrder;
import com.github.saphyra.skyxplore.game.newround.production.Producer;
import com.github.saphyra.skyxplore.game.newround.production.ProducerQueryService;
import com.github.saphyra.skyxplore.game.newround.production.ProducerSelector;
import com.github.saphyra.skyxplore.game.newround.production.ongoing.OngoingProductionOrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO refactor - split
public class TerraformOrderProcessor {
    private final ConstructionCommandService constructionCommandService;
    private final HumanResourceService humanResourceService;
    private final OngoingProductionOrderQueryService ongoingProductionOrderQueryService;
    private final ProducerQueryService producerQueryService;
    private final ProducerSelector producerSelector;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final SurfaceCommandService surfaceCommandService;
    private final SurfaceQueryService surfaceQueryService;

    public void process(TerraformOrder terraformOrder) {
        log.info("Processing terraformOrder {}", terraformOrder);

        Construction construction = terraformOrder.getConstruction();
        if (construction.getConstructionStatus() == ConstructionStatus.QUEUED) {
            handleQueued(terraformOrder);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.RESOURCE_COLLECTION) {
            handleResourceCollection(construction);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.IN_PROGRESS) {
            Optional<HumanResource> optionalHumanResource = humanResourceService.getOne(
                construction.getGameId(),
                construction.getStarId(),
                construction.getConstructionId(),
                SkillType.BUILDING
            );
            if (optionalHumanResource.isPresent()) {
                HumanResource humanResource = optionalHumanResource.get();
                humanResource.setAllocation(construction.getConstructionId());
                ProductionProcess productionProcess = humanResource.produce(
                    SkillType.BUILDING,
                    1,
                    construction.getConstructionRequirements().getRequiredWorkPoints(),
                    construction.getCurrentWorkPoints()
                );
                if (productionProcess.getFinishedProducts() == 1) {
                    Surface surface = surfaceQueryService.findBySurfaceIdAndPlayerId(construction.getExternalId());
                    surface.setSurfaceType(SurfaceType.valueOf(construction.getAdditionalData()));
                    surfaceCommandService.save(surface);
                    constructionCommandService.delete(construction);
                } else {
                    construction.setCurrentWorkPoints(construction.getCurrentWorkPoints() + productionProcess.getCurrentProgress());
                    constructionCommandService.save(construction);
                }
            }
        }
    }

    private void handleQueued(TerraformOrder terraformOrder) {
        List<ProductionOrder> productionOrders = ongoingProductionOrderQueryService.getRequirementOrders(terraformOrder.getConstruction());
        productionOrderCommandService.saveAll(productionOrders);
        terraformOrder.getConstruction().setConstructionStatus(ConstructionStatus.RESOURCE_COLLECTION);
    }

    private void handleResourceCollection(Construction construction) {
        List<ProductionOrder> orders = productionOrderQueryService.getByCustomerIdAndPlayerId(construction.getConstructionId());
        Set<UUID> depletedProducerIds = new HashSet<>();
        for (ProductionOrder order : orders) {
            Map<UUID, Producer> producers = producerQueryService.getByStarIdAndDataId(construction.getStarId(), order.getDataId())
                .stream()
                .collect(Collectors.toMap(Producer::getId, Function.identity()));
            Optional<Producer> optionalProducer = producerSelector.selectProducer(order, producers, depletedProducerIds);
            if (optionalProducer.isPresent()) {
                Producer producer = optionalProducer.get();
                boolean depleted = producer.produce(order);
                if (depleted) {
                    depletedProducerIds.add(producer.getId());
                }
                if (order.isReady()) {
                    construction.addResource(order.getDataId());
                    productionOrderCommandService.delete(order);
                } else {
                    productionOrderCommandService.save(order);
                }
            } else {
                log.info("Available Producer not found for dataId {}", order.getDataId());
            }
        }

        if (construction.isResourcesAvailable()) {
            construction.setConstructionStatus(ConstructionStatus.IN_PROGRESS);
        }

        constructionCommandService.save(construction);
    }
}
