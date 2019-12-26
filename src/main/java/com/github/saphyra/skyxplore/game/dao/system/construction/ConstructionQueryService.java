package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
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

    public Construction findByConstructionIdAndGameIdAndPlayerId(UUID constructionId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return constructionDao.findByConstructionIdAndGameIdAndPlayerId(constructionId, gameId, playerId)
            .orElseThrow(() -> ExceptionFactory.constructionNotFound(constructionId, gameId, playerId));
    }

    public Optional<Construction> findByConstructionTypeAndExternalIdAndGameIdAndPlayerId(ConstructionType constructionType, UUID externalId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return constructionDao.findByConstructionTypeAndExternalIdAndGameIdAndPlayerId(constructionType, externalId, gameId, playerId);
    }

    public Optional<Construction> findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(ConstructionType constructionType, UUID surfaceId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return constructionDao.findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(constructionType, surfaceId, gameId, playerId);
    }

    public List<Construction> getByStarIdAndGameIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return constructionDao.getByStarIdAndGameIdAndPlayerId(starId, gameId, playerId);
    }
}
