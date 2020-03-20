package com.github.saphyra.skyxplore.app.domain.star_connection;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StarConnectionQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StarConnectionDao starConnectionDao;

    public List<StarConnection> getByGameId() {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        return starConnectionDao.getByGameId(gameId);
    }
}
