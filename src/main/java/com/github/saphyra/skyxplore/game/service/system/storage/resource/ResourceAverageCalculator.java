package com.github.saphyra.skyxplore.game.service.system.storage.resource;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceAverageCalculator {
    private final ResourceQueryService resourceQueryService;

    public int getAverage(Resource resource) {
        double average = resourceQueryService.getByStarIdAndDataIdAndGameIdAndPlayerId(resource.getStarId(), resource.getDataId()).stream()
                .sorted((o1, o2) -> -o1.getRound().compareTo(o2.getRound()))
                .limit(10)
                .mapToInt(Resource::getAmount)
                .average()
                .orElse(0);
        return (int) Math.round(average);
    }
}
