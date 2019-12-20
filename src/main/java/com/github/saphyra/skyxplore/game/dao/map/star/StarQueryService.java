package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
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

    public Star findByStarIdAndGameIdAndOwnerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return starDao.findByStarIdAndGameIdAndOwnerId(starId, gameId, playerId)
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId));
    }

    public List<Star> getByGameIdAndOwnerId() {
        RequestContext requestContext = requestContextHolder.get();
        UUID playerId = requestContext.getPlayerId();
        UUID gameId = requestContext.getGameId();
        return starDao.getByGameIdAndPlayerId(gameId, playerId);
    }

    public Coordinate getCoordinateOfStar(UUID starId) {
        return starDao.findById(uuidConverter.convertDomain(starId))
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId))
            .getCoordinate();
    }
}
