package com.github.saphyra.skyxplore.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.newround.resource.spend.ResourceSpendDetails;
import com.github.saphyra.skyxplore.game.newround.resource.spend.ResourceSpendService;
import com.github.saphyra.util.OptionalMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class AllocationResolver {
    private final ResourceSpendService resourceSpendService;

    void resolveAllocations(OptionalMap<String, Allocation> allocations) {
        for (Allocation allocation : allocations.values()) {
            ResourceSpendDetails spendDetails = resourceSpendService.getSpendDetails(allocation.getStarId(), allocation.getDataId(), allocation.getAmount(), allocation.getAllocationId());
            spendDetails.setAllocationId(allocation.getAllocationId());
            resourceSpendService.spend(spendDetails);
        }
    }
}
