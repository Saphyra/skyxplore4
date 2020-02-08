package com.github.saphyra.skyxplore.game.newround.resource;

import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageSettingReservationUpdateService {
    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;
    private final ReservationService reservationService;
    private final StorageQueryService storageQueryService;
    private final StorageSettingQueryService storageSettingQueryService;

    public void updateForStar(Star star) {
        List<StorageSetting> storageSettings = storageSettingQueryService.getByStarIdAndPlayerId(star.getStarId());
        for (StorageSetting storageSetting : storageSettings) {
            log.info("Updating reservations for StorageSetting {}", storageSetting);
            int availableResource = storageQueryService.getAvailableResourceByStarIdAndDataIdAndPlayerId(star.getStarId(), storageSetting.getDataId());
            int missingResource = storageSetting.getTargetAmount() - availableResource;
            log.info("available {}: {}, missing: {}", storageSetting.getDataId(), availableResource, missingResource);
            Optional<Reservation> reservationOptional = reservationQueryService.findByExternalReferenceAndDataIdAndPlayerId(storageSetting.getStorageSettingId(), storageSetting.getDataId());
            if (reservationOptional.isPresent()) {
                Reservation reservation = reservationOptional.get();
                log.info("Reservation found: {}", reservation);
                if (missingResource > 0) {
                    log.info("There are missing resources. Updating reservation...");
                    reservation.setAmount(missingResource);
                    reservationCommandService.save(reservation);
                } else {
                    log.info("There is no missing resource. Deleting reservation...");
                    reservationCommandService.delete(reservation);
                }
            } else if (missingResource > 0) {
                log.info("Reservation not found, and there are missing resources. Creating reservation...");
                reservationService.reserve(
                    storageSetting.getGameId(),
                    storageSetting.getStarId(),
                    storageSetting.getDataId(),
                    missingResource,
                    ReservationType.STORAGE_SETTING,
                    storageSetting.getStorageSettingId(),
                    storageSetting.getPlayerId()
                );
            }
        }
    }
}
