package com.github.saphyra.skyxplore_deprecated.game.dao.map.star;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StarQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StarDao starDao;
    private final UuidConverter uuidConverter;

    public Star findByStarIdAndOwnerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return starDao.findByStarIdAndOwnerId(starId, playerId)
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId));
    }

    public List<Star> getByOwnerId() {
        RequestContext requestContext = requestContextHolder.get();
        UUID playerId = requestContext.getPlayerId();
        return getByOwnerId(playerId);
    }

    public List<Star> getByOwnerId(UUID playerId) {
        return starDao.getByOwnerId(playerId);
    }

    public Coordinate getCoordinateOfStar(UUID starId) {
        return starDao.findById(uuidConverter.convertDomain(starId))
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId))
            .getCoordinate();
    }
}
