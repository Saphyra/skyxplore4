package com.github.saphyra.skyxplore.game.module.system.resource.service;

import com.github.saphyra.skyxplore.game.module.system.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.module.system.resource.domain.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceAverageCalculator {
    private final ResourceQueryService resourceQueryService;

    public int getAverage(Resource resource) {
        double average = resourceQueryService.getByStarIdAndDataId(resource.getStarId(), resource.getDataId()).stream()
                .sorted((o1, o2) -> -o1.getRound().compareTo(o2.getRound()))
                .limit(10)
                .mapToInt(Resource::getAmount)
                .average()
                .orElse(0);
        return (int) Math.round(average);
    }
}
