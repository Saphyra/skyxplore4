package com.github.saphyra.skyxplore_deprecated.game.dao.system.construction;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConstructionQueryService {
    private final ConstructionDao constructionDao;
    private final RequestContextHolder requestContextHolder;

    public Construction findByConstructionIdAndPlayerId(UUID constructionId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return constructionDao.findByConstructionIdAndPlayerId(constructionId, playerId)
            .orElseThrow(() -> ExceptionFactory.constructionNotFound(constructionId, playerId));
    }

    public Optional<Construction> findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType constructionType, UUID externalId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return constructionDao.findByConstructionTypeAndExternalIdAndPlayerId(constructionType, externalId, playerId);
    }

    public Optional<Construction> findByConstructionTypeAndSurfaceIdAndPlayerId(ConstructionType constructionType, UUID surfaceId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return constructionDao.findByConstructionTypeAndSurfaceIdAndPlayerId(constructionType, surfaceId, playerId);
    }

    public List<Construction> getByStarIdAndGameIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return constructionDao.getByStarIdAndPlayerId(starId, playerId);
    }

    public List<Construction> getByStarIdAndConstructionTypeAndPlayerId(UUID starId, ConstructionType constructionType) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return constructionDao.getByStarIdAndConstructionTypeAndPlayerId(starId, constructionType, playerId);
    }
}
