package com.github.saphyra.skyxplore.app.domain.star;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
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
