package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCommandService implements CommandService<Reservation> {
    private final RequestContextHolder requestContextHolder;
    private final ReservationDao reservationDao;

    @Override
    public void delete(Reservation domain) {
        reservationDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        reservationDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Reservation reservation) {
        reservationDao.save(reservation);
    }

    public void deleteByExternalReferenceAndGameIdAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        reservationDao.deleteByExternalReferenceAndGameIdAndPlayerId(externalReference, gameId, playerId);
    }

    @Override
    public void saveAll(List<Reservation> domains) {
        reservationDao.saveAll(domains);
    }

    @Override
    public Class<Reservation> getType() {
        return Reservation.class;
    }
}
