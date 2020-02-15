package com.github.saphyra.skyxplore_deprecated.game.service.system.costruction;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.ConstructionStatusView;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.StorageQueryService;
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
        Construction construction = constructionQueryService.findByConstructionIdAndPlayerId(constructionId);
        return findByConstruction(construction);
    }

    public ConstructionStatusView findByConstruction(Construction construction) {
        return ConstructionStatusView.builder()
            .constructionId(construction.getConstructionId())
            .status(construction.getConstructionStatus())
            .currentWorkPoints(construction.getCurrentWorkPoints())
            .requiredWorkPoints(construction.getCurrentWorkPoints())
            .requiredResourcesAmount(calculateRequiredResourcesAmount(construction.getConstructionRequirements().getRequiredResources()))
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
