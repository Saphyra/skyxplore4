package com.github.saphyra.skyxplore_deprecated.game.service.system.storage.reservation;

import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationFactory {
    private final IdGenerator idGenerator;
    private final ResourceDataService resourceDataService;

    public Reservation create(UUID gameId, UUID starId, String dataId, int amount, ReservationType reservationType, UUID externalReference, UUID playerId) {
        return Reservation.builder()
            .reservationId(idGenerator.randomUUID())
            .gameId(gameId)
            .starId(starId)
            .playerId(playerId)
            .dataId(dataId)
            .amount(amount)
            .storageType(resourceDataService.get(dataId).getStorageType())
            .reservationType(reservationType)
            .externalReference(externalReference)
            .isNew(true)
            .build();
    }
}
