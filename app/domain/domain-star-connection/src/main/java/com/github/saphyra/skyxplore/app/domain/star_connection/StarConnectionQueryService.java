package com.github.saphyra.skyxplore.app.domain.star_connection;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
//TODO unit test
public class StarConnectionQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StarConnectionDao starConnectionDao;

    public List<StarConnection> getByGameIdAndUserId() {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        return starConnectionDao.getByGameId(gameId);
    }
}
