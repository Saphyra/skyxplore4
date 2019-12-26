package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCommandService {
    private final RequestContextHolder requestContextHolder;
    private final ReservationDao reservationDao;

    public void save(Reservation reservation) {
        reservationDao.save(reservation);
    }

    public void deleteByExternalReferenceAndGameIdAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        reservationDao.deleteByExternalReferenceAndGameIdAndPlayerId(externalReference, gameId, playerId);
    }
}
