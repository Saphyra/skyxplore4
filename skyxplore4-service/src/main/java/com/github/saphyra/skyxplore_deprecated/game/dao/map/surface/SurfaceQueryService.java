package com.github.saphyra.skyxplore_deprecated.game.dao.map.surface;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SurfaceQueryService {
    private final RequestContextHolder requestContextHolder;
    private final SurfaceDao surfaceDao;

    public Surface findBySurfaceIdAndPlayerId(UUID surfaceId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return surfaceDao.findBySurfaceIdAndPlayerId(surfaceId, playerId)
            .orElseThrow(() -> ExceptionFactory.surfaceNotFound(surfaceId));
    }

    public List<Surface> getByStarIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return surfaceDao.getByStarIdAndPlayerId(starId, playerId);
    }
}
