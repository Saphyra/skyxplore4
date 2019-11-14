package com.github.saphyra.skyxplore.game.module.system.storage.reservation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDao reservationDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        reservationDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
