package com.github.saphyra.skyxplore.game.newround.resource.cleanup;

import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AllocationResourceCleaner implements ResourceCleaner {
    private final AllocationCommandService allocationCommandService;
    private final AllocationQueryService allocationQueryService;

    @Override
    public void clean() {
        List<Allocation> allocations = allocationQueryService.getByGameId()
            .stream()
            .filter(allocation -> allocation.getAmount() == 0)
            .collect(Collectors.toList());

        allocationCommandService.deleteAll(allocations);
    }
}
