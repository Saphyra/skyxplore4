package com.github.saphyra.skyxplore_deprecated.game.service.system.storage.resource;

import com.github.saphyra.skyxplore_deprecated.game.dao.game.GameQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.ResourceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceAverageCalculator {
    private final GameQueryService gameQueryService;
    private final ResourceQueryService resourceQueryService;

    public int getAverage(Resource resource) {
        int round = gameQueryService.findByGameIdAndUserIdValidated().getRound();
        int sum = resourceQueryService.getByStarIdAndDataIdAndPlayerId(resource.getStarId(), resource.getDataId()).stream()
            .sorted((o1, o2) -> o2.getRound().compareTo(o1.getRound()))
            .limit(10)
            .mapToInt(Resource::getAmount)
            .sum();

        return sum / Math.min(round, 10);
    }
}
