package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationQueryService {
    private final RequestContextHolder requestContextHolder;
    private final ReservationDao reservationDao;

    public List<Reservation> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return reservationDao.getByStarIdAndDataIdAndGameIdAndPlayerId(starId, dataId, gameId, playerId);
    }

    public List<Reservation> getByStarIdAndStorageTypeAndGameIdAndPlayerId(UUID starId, StorageType storageType) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return reservationDao.getByStarIdAndStorageTypeAndGameIdAndPlayerId(starId, storageType, gameId, playerId);
    }
}
