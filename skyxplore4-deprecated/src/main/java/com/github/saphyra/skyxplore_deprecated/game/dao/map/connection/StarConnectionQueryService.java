package com.github.saphyra.skyxplore_deprecated.game.dao.map.connection;

import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StarConnectionQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StarConnectionDao starConnectionDao;

    public List<StarConnection> getByGameIdAndUserId() {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID userId = context.getUserId();
        return starConnectionDao.getByGameIdAndUserId(gameId);
    }
}
