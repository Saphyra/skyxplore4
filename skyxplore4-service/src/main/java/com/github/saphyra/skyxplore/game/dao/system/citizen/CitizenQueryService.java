package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitizenQueryService {
    private final CitizenDao citizenDao;
    private final RequestContextHolder requestContextHolder;

    public Integer countByLocationAndOwnerId(LocationType locationType, UUID locationId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return citizenDao.countByLocationAndAndOwnerId(locationType, locationId, playerId);
    }

    public Citizen findByCitizenIdAndOwnerId(UUID citizenId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return citizenDao.findByCitizenIdAndOwnerId(citizenId, playerId)
            .orElseThrow(() -> ExceptionFactory.citizenNotFound(citizenId, playerId));
    }

    public List<Citizen> getByLocationIdAndOwnerId(UUID locationId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return citizenDao.getByLocationIdAndOwnerId(locationId, playerId);
    }
}
