package com.github.saphyra.skyxplore.app.domain.surface;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
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
