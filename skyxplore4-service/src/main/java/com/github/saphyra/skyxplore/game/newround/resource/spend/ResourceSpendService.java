package com.github.saphyra.skyxplore.game.newround.resource.spend;

import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceSpendService {
    private final AllocationCommandService allocationCommandService;
    private final AllocationQueryService allocationQueryService;
    private final ResourceCommandService resourceCommandService;
    private final ResourceQueryService resourceQueryService;

    public ResourceSpendDetails getSpendDetails(UUID starId, String dataId, int amount) {
        return getSpendDetails(starId, dataId, amount, null);
    }

    public ResourceSpendDetails getSpendDetails(UUID starId, String dataId, int amount, UUID allocationId) {
        return resourceQueryService.findByStarIdAndDataIdAndRoundAndPlayerId(starId, dataId)
            .map(resource -> getDetails(resource, amount, allocationId))
            .orElseGet(() -> getDetails(starId, dataId, amount));
    }

    private ResourceSpendDetails getDetails(Resource resource, int amount, UUID allocationId) {
        Map<UUID, Allocation> allocations = allocationQueryService.getByStarIdAndDataIdAndPlayerId(resource.getStarId(), resource.getDataId())
            .stream()
            .collect(Collectors.toMap(Allocation::getAllocationId, Function.identity()));
        int allocated = allocations.values()
            .stream()
            .filter(allocation -> !isNull(allocationId) && !allocation.getAllocationId().equals(allocationId))
            .mapToInt(Allocation::getAmount)
            .sum();
        return ResourceSpendDetails.builder()
            .starId(resource.getStarId())
            .dataId(resource.getDataId())
            .requestedAmount(amount)
            .availableAmount(resource.getAmount() - allocated)
            .build();
    }

    private ResourceSpendDetails getDetails(UUID starId, String dataId, int amount) {
        return ResourceSpendDetails.builder()
            .starId(starId)
            .dataId(dataId)
            .requestedAmount(amount)
            .availableAmount(0)
            .build();
    }

    @Transactional
    public void spend(ResourceSpendDetails spendDetails) {
        Resource resource = resourceQueryService.findByStarIdAndDataIdAndRoundAndPlayerIdValidated(spendDetails.getStarId(), spendDetails.getDataId());
        resource.removeAmount(spendDetails.getToSpend());
        resourceCommandService.save(resource);
        Optional.ofNullable(spendDetails.getAllocationId())
            .ifPresent(allocationCommandService::delete);
    }
}
