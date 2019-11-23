package com.github.saphyra.skyxplore.game.service.system.costruction;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.rest.view.ConstructionStatusView;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConstructionViewQueryService {
    private final ConstructionQueryService constructionQueryService;
    private final StorageQueryService storageQueryService;

    public ConstructionStatusView findByConstructionId(UUID constructionId) {
        Construction construction = constructionQueryService.findByConstructionId(constructionId);
        return ConstructionStatusView.builder()
            .constructionId(constructionId)
            .status(construction.getConstructionStatus())
            .currentWorkPoints(construction.getCurrentWorkPoints())
            .requiredWorkPoints(construction.getConstructionRequirements().getWorkPoints())
            .requiredResourcesAmount(calculateRequiredResourcesAmount(construction.getConstructionRequirements().getResources()))
            .allocatedResourcesAmount(fetchAllocatedResourcesAmount(construction.getConstructionId()))
            .build();
    }

    private Integer calculateRequiredResourcesAmount(Map<String, Integer> resources) {
        return resources.values().stream().mapToInt(Integer::intValue).sum();
    }

    private Integer fetchAllocatedResourcesAmount(UUID constructionId) {
        return storageQueryService.getAllocationsByExternalReference(constructionId).stream()
            .mapToInt(Allocation::getAmount)
            .sum();
    }
}
