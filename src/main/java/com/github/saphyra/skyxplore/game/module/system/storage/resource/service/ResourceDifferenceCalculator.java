package com.github.saphyra.skyxplore.game.module.system.storage.resource.service;

import com.github.saphyra.skyxplore.game.module.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceDifferenceCalculator {
    private final ResourceQueryService resourceQueryService;

    public Integer getDifference(Resource resource) {
        int earlierAmount = resourceQueryService.findByStarIdDataIdAndRound(resource.getStarId(), resource.getDataId(), resource.getRound() - 1)
                .map(Resource::getAmount)
                .orElse(0);

        return resource.getAmount() - earlierAmount;
    }
}
