package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
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
        UUID playerId = context.getPlayerId();
        return reservationDao.getByStarIdAndDataIdAndPlayerId(starId, dataId, playerId);
    }

    public List<Reservation> getByStarIdAndStorageTypeAndPlayerId(UUID starId, StorageType storageType) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return reservationDao.getByStarIdAndStorageTypeAndPlayerId(starId, storageType, playerId);
    }

    public Reservation findByExternalReferenceAndDataIdAndPlayerIdValidated(UUID externalReference, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return reservationDao.findByExternalReferenceAndDataIdAndPlayerId(externalReference, dataId, playerId)
            .orElseThrow(() -> ExceptionFactory.reservationNotFoundByExternalReferenceAndDataId(externalReference, dataId, playerId));
    }
}
