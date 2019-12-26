package com.github.saphyra.skyxplore.game.service.system.storage.reservation;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationDao reservationDao;
    private final IdGenerator idGenerator;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting reservations for event {}", event);
        reservationDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }

    public void reserve(UUID gameId, UUID userId, UUID starId, String dataId, int amount, StorageType storageType, ReservationType reservationType, UUID externalReference) {
        reservationDao.save(
                Reservation.builder()
                        .reservationId(idGenerator.randomUUID())
                        .gameId(gameId)
                        .userId(userId)
                        .starId(starId)
                        .dataId(dataId)
                        .amount(amount)
                        .storageType(storageType)
                        .reservationType(reservationType)
                    .externalReference(externalReference)
                        .build()
        );
    }
}
