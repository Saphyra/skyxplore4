package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SurfaceQueryService {
    private final RequestContextHolder requestContextHolder;
    private final SurfaceDao surfaceDao;

    public Surface findBySurfaceIdAndGameIdAndPlayerId(UUID surfaceId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return surfaceDao.findBySurfaceIdAndGameIdAndPlayerId(surfaceId, gameId, playerId)
            .orElseThrow(() -> ExceptionFactory.surfaceNotFound(surfaceId));
    }

    public List<Surface> getByStarIdAndGameIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return surfaceDao.getByStarIdAndGameIdAndPlayerId(starId, gameId, playerId);
    }
}
