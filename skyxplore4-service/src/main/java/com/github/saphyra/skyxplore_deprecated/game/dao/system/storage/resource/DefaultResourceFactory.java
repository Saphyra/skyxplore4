package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore_deprecated.game.dao.game.GameQueryService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultResourceFactory {
    private final GameQueryService gameQueryService;
    private final IdGenerator idGenerator;
    private final RequestContextHolder requestContextHolder;
    private final ResourceDataService resourceDataService;

    public Resource create(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        return Resource.builder()
            .resourceId(idGenerator.randomUUID())
            .gameId(context.getGameId())
            .playerId(context.getPlayerId())
            .storageType(resourceDataService.get(dataId).getStorageType())
            .dataId(dataId)
            .starId(starId)
            .amount(0)
            .round(gameQueryService.findByGameIdAndUserIdValidated().getRound())
            .isNew(true)
            .build();
    }
}
