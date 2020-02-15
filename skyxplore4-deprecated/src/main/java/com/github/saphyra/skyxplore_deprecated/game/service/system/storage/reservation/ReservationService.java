package com.github.saphyra.skyxplore_deprecated.game.service.system.storage.reservation;

import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationCommandService reservationCommandService;
    private final ResourceDataService resourceDataService;
    private final ReservationFactory reservationFactory;

    public void reserve(UUID gameId, UUID starId, String dataId, int amount, ReservationType reservationType, UUID externalReference, UUID playerId) {
        StorageType storageType = resourceDataService.get(dataId).getStorageType();
        reserve(gameId, starId, dataId, amount, storageType, reservationType, externalReference, playerId);
    }

    public void reserve(UUID gameId, UUID starId, String dataId, int amount, StorageType storageType, ReservationType reservationType, UUID externalReference, UUID playerId) {
        Reservation reservation = reservationFactory.create(gameId, starId, dataId, amount, reservationType, externalReference, playerId);
        reservationCommandService.save(reservation);
    }
}
