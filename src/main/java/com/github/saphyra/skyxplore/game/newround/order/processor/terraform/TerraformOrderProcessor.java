package com.github.saphyra.skyxplore.game.newround.order.processor.terraform;

import com.github.saphyra.skyxplore.common.OptionalHashMap;
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
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResource;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import com.github.saphyra.skyxplore.game.newround.hr.ProductionProcess;
import com.github.saphyra.skyxplore.game.newround.order.ProductionOrderFactory;
import com.github.saphyra.skyxplore.game.newround.order.TerraformOrder;
import com.github.saphyra.skyxplore.game.newround.production.Producer;
import com.github.saphyra.skyxplore.game.newround.production.ProducerQueryService;
import com.github.saphyra.skyxplore.game.newround.resource.spend.ResourceSpendDetails;
import com.github.saphyra.skyxplore.game.newround.resource.spend.ResourceSpendService;
import com.github.saphyra.skyxplore.game.service.system.storage.allocation.AllocationFactory;
import com.github.saphyra.skyxplore.game.service.system.storage.reservation.ReservationFactory;
import com.github.saphyra.util.OptionalMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO refactor - split
public class TerraformOrderProcessor {
    private final AllocationFactory allocationFactory;
    private final AllocationCommandService allocationCommandService;
    private final AllocationQueryService allocationQueryService;
    private final ConstructionCommandService constructionCommandService;
    private final HumanResourceService humanResourceService;
    private final ProducerQueryService producerQueryService;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderFactory productionOrderFactory;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ReservationCommandService reservationCommandService;
    private final ReservationFactory reservationFactory;
    private final ReservationQueryService reservationQueryService;
    private final ResourceCommandService resourceCommandService;
    private final ResourceQueryService resourceQueryService;
    private final ResourceSpendService resourceSpendService;
    private final SurfaceCommandService surfaceCommandService;
    private final SurfaceQueryService surfaceQueryService;

    public void process(TerraformOrder terraformOrder) {
        log.info("Processing terraformOrder {}", terraformOrder);

        Construction construction = terraformOrder.getConstruction();
        if (construction.getConstructionStatus() == ConstructionStatus.QUEUED) {
            construction.setConstructionStatus(ConstructionStatus.RESOURCE_COLLECTION);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.RESOURCE_COLLECTION) {
            handleResourceCollection(construction);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.IN_PROGRESS) {
            handleInProgress(construction);
        }
    }

    private void handleResourceCollection(Construction construction) {
        Map<String, Integer> requiredResources = construction.getConstructionRequirements().getRequiredResources();

        OptionalMap<String, Allocation> allocations = getAllocations(construction, requiredResources);
        List<ProductionOrder> productionOrders = getProductionOrders(construction, allocations);
        produceResources(construction, productionOrders);
        finishProductions(construction, allocations, productionOrders);

        boolean allResourcePresent = updateReservations(construction, requiredResources, allocations);
        if (allResourcePresent) {
            for (Allocation allocation : allocations.values()) {
                ResourceSpendDetails spendDetails = resourceSpendService.getSpendDetails(construction.getStarId(), allocation.getDataId(), allocation.getAmount(), allocation.getAllocationId());
                spendDetails.setAllocationId(allocation.getAllocationId());
                resourceSpendService.spend(spendDetails);
            }
            construction.setConstructionStatus(ConstructionStatus.IN_PROGRESS);
        }

        constructionCommandService.save(construction);
    }

    private boolean updateReservations(Construction construction, Map<String, Integer> requiredResources, OptionalMap<String, Allocation> allocations) {
        boolean allResourcesPresent = true;
        for (String dataId : requiredResources.keySet()) {
            int requiredAmount = requiredResources.get(dataId);
            int amountToReserve = requiredAmount - allocations.get(dataId).getAmount();

            Optional<Reservation> optionalReservation = reservationQueryService.findByExternalReferenceAndDataIdAndPlayerId(construction.getConstructionId(), dataId);
            if (amountToReserve > 0) {
                allResourcesPresent = false;
                Reservation reservation = optionalReservation.orElseGet(() -> reservationFactory.create(
                    construction.getGameId(),
                    construction.getStarId(),
                    dataId,
                    0,
                    ReservationType.TERRAFORMING,
                    construction.getConstructionId(),
                    construction.getPlayerId()
                ));

                reservation.setAmount(amountToReserve);
                reservationCommandService.save(reservation);
            } else {
                optionalReservation.ifPresent(reservationCommandService::delete);
            }
        }
        return allResourcesPresent;
    }

    private OptionalMap<String, Allocation> getAllocations(Construction construction, Map<String, Integer> requiredResources) {
        OptionalMap<String, Allocation> allocations = new OptionalHashMap<>(
            allocationQueryService.getByExternalReferenceAndPlayerId(construction.getConstructionId())
                .stream()
                .collect(Collectors.toMap(Allocation::getDataId, Function.identity()))
        );

        for (String dataId : requiredResources.keySet()) {
            int requiredAmount = requiredResources.get(dataId);
            Optional<Allocation> optionalAllocation = allocations.getOptional(dataId);
            int allocatedAmount = optionalAllocation.map(Allocation::getAmount).orElse(0);
            int missingAmount = requiredAmount - allocatedAmount;
            ResourceSpendDetails details = resourceSpendService.getSpendDetails(construction.getStarId(), dataId, missingAmount);
            int availableAmount = details.getToSpend();
            Allocation allocation = optionalAllocation.orElseGet(() -> allocationFactory.allocate(
                construction.getGameId(),
                construction.getStarId(),
                construction.getConstructionId(),
                dataId,
                Math.max(allocatedAmount, allocatedAmount + availableAmount),
                AllocationType.CONSTRUCTION,
                construction.getPlayerId()
            ));
            allocations.put(dataId, allocation);
        }
        allocationCommandService.saveAll(new ArrayList<>(allocations.values()));
        return allocations;
    }

    private List<ProductionOrder> getProductionOrders(Construction construction, OptionalMap<String, Allocation> allocations) {
        OptionalMap<String, ProductionOrder> productionOrders = new OptionalHashMap<>(
            productionOrderQueryService.getByCustomerIdAndPlayerId(construction.getConstructionId())
                .stream()
                .collect(Collectors.toMap(ProductionOrder::getDataId, Function.identity()))
        );
        for (String dataId : allocations.keySet()) {
            int requiredAmount = construction.getConstructionRequirements().getRequiredResources().get(dataId);
            Allocation allocation = allocations.get(dataId);
            int missingAmount = requiredAmount - allocation.getAmount();
            Optional<ProductionOrder> optionalProductionOrder = productionOrders.getOptional(dataId);
            if (missingAmount > 0) {
                ProductionOrder productionOrder = optionalProductionOrder.orElseGet(() -> productionOrderFactory.create(construction, dataId, missingAmount));
                productionOrder.setTargetAmount(missingAmount);
                productionOrders.put(dataId, productionOrder);
            } else {
                optionalProductionOrder.ifPresent(order -> order.setTargetAmount(0));
            }
        }
        List<ProductionOrder> productionOrderList = new ArrayList<>(productionOrders.values());
        productionOrderCommandService.saveAll(productionOrderList);
        return new ArrayList<>(productionOrderList);
    }

    private void produceResources(Construction construction, List<ProductionOrder> productionOrders) {
        for (ProductionOrder productionOrder : productionOrders) {
            List<Producer> producers = producerQueryService.getByStarIdAndDataId(construction.getStarId(), productionOrder.getDataId());
            for (int i = 0; i < producers.size() && !productionOrder.isReady(); i++) {
                producers.get(i).produce(productionOrder);
            }
        }
        productionOrderCommandService.saveAll(productionOrders);
    }

    private void finishProductions(Construction construction, OptionalMap<String, Allocation> allocations, List<ProductionOrder> productionOrders) {
        for (ProductionOrder productionOrder : productionOrders) {
            if (productionOrder.isReady()) {
                int producedAmount = productionOrder.getTargetAmount();

                Resource resource = resourceQueryService.findByStarIdAndDataIdAndRoundAndPlayerIdValidated(construction.getStarId(), productionOrder.getDataId());
                resource.addAmount(producedAmount);
                resourceCommandService.save(resource);

                Allocation allocation = allocations.get(productionOrder.getDataId());
                allocation.addAmount(producedAmount);
                allocationCommandService.save(allocation);

                productionOrderCommandService.delete(productionOrder);
            }
        }
    }

    private void handleInProgress(Construction construction) {
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
