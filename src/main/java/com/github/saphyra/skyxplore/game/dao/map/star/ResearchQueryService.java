package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResearchQueryService {
    private final RequestContextHolder requestContextHolder;
    private final ResearchDao researchDao;

    public List<Research> getByStarIdAndGameIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return researchDao.getByStarIdAndGameIdAndPlayerId(starId, gameId, playerId);
    }
}
