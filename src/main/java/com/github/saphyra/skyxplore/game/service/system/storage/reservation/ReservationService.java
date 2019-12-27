package com.github.saphyra.skyxplore.game.service.system.storage.reservation;

import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationCommandService reservationCommandService;
    private final IdGenerator idGenerator;

    public void reserve(UUID gameId, UUID starId, String dataId, int amount, StorageType storageType, ReservationType reservationType, UUID externalReference, UUID playerId) {
        reservationCommandService.save(
            Reservation.builder()
                .reservationId(idGenerator.randomUUID())
                .gameId(gameId)
                .starId(starId)
                .playerId(playerId)
                .dataId(dataId)
                .amount(amount)
                .storageType(storageType)
                .reservationType(reservationType)
                .externalReference(externalReference)
                .build()
        );
    }
}
