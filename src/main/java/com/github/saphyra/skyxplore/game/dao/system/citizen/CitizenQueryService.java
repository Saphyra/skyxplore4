package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitizenQueryService {
    private final CitizenDao citizenDao;
    private final RequestContextHolder requestContextHolder;

    public Integer countByLocationAndGameIdAndOwnerId(LocationType locationType, UUID locationId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return citizenDao.countByLocationAndGameIdAndOwnerId(locationType, locationId, gameId, playerId);
    }
}
