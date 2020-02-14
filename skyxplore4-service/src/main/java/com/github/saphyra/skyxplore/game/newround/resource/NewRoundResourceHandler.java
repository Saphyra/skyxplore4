package com.github.saphyra.skyxplore.game.newround.resource;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.newround.resource.cleanup.ResourceCleaner;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewRoundResourceHandler {
    private static final Integer RESOURCE_EXPIRATION_ROUNDS = 10;

    private final IdGenerator idGenerator;
    private final List<ResourceCleaner> resourceCleaners;
    private final RequestContextHolder requestContextHolder;
    private final ResourceCommandService resourceCommandService;
    private final ResourceQueryService resourceQueryService;

    public void cleanUpResources(UUID gameId) {
        requestContextHolder.setContext(RequestContext.builder().gameId(gameId).build());
        resourceCleaners.forEach(ResourceCleaner::clean);
    }

    public void prepareForNewRound(Integer round) {
        Integer newRound = round + 1;
        List<Resource> resources = resourceQueryService.getByGameIdAndRound(round)
            .stream()
            .parallel()
            .map(resource -> duplicate(resource, newRound))
            .collect(Collectors.toList());
        resourceCommandService.saveAll(resources);
    }

    private Resource duplicate(Resource resource, Integer newRound) {
        return resource.toBuilder()
            .resourceId(idGenerator.randomUUID())
            .isNew(true)
            .round(newRound)
            .build();
    }

    public void deleteExpiredResources(Integer round) {
        resourceCommandService.deleteExpiredByGameId(round - RESOURCE_EXPIRATION_ROUNDS);
    }
}
