package com.github.saphyra.skyxplore_deprecated.game.dao.system.priority;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class PriorityQueryService {
    private final PriorityDao priorityDao;
    private final RequestContextHolder requestContextHolder;

    public List<Priority> getByStarIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return priorityDao.getByStarIdAndPlayerId(starId, playerId);
    }

    public Priority findByStarIdAndPriorityTypeAndPlayerIdValidated(UUID starId, PriorityType type) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return priorityDao.findByStarIdAndPriorityTypeAndPlayerId(starId, type, playerId)
            .orElseThrow(() -> ExceptionFactory.priorityNotFound(starId, type, playerId));
    }
}
