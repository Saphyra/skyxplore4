package com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore.common.utils.OptionalHashMap;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.AllocationQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.skyxplore_deprecated.game.newround.resource.spend.ResourceSpendDetails;
import com.github.saphyra.skyxplore_deprecated.game.newround.resource.spend.ResourceSpendService;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.allocation.AllocationFactory;
import com.github.saphyra.util.OptionalMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class AllocationHandler {
    private final AllocationFactory allocationFactory;
    private final AllocationCommandService allocationCommandService;
    private final AllocationQueryService allocationQueryService;
    private final ResourceSpendService resourceSpendService;

    OptionalMap<String, Allocation> getAllocations(Construction construction, Map<String, Integer> requiredResources) {
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
            Allocation allocation = optionalAllocation.orElseGet(() -> createAllocation(
                construction,
                dataId,
                allocatedAmount,
                availableAmount)
            );
            allocations.put(dataId, allocation);
        }
        allocationCommandService.saveAll(new ArrayList<>(allocations.values()));
        return allocations;
    }

    private Allocation createAllocation(Construction construction, String dataId, int allocatedAmount, int availableAmount) {
        return allocationFactory.allocate(
            construction.getGameId(),
            construction.getStarId(),
            construction.getConstructionId(),
            dataId,
            Math.max(allocatedAmount, allocatedAmount + availableAmount),
            AllocationType.CONSTRUCTION,
            construction.getPlayerId()
        );
    }
}
