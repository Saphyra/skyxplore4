package com.github.saphyra.skyxplore.game.service.system.storage.setting.update;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.rest.request.UpdateStorageSettingsRequest;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.reservation.ReservationFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageSettingUpdateService {
    private final ReservationCommandService reservationCommandService;
    private final ReservationFactory reservationFactory;
    private final ReservationQueryService reservationQueryService;
    private final ResourceDataService resourceDataService;
    private final ResourceQueryService resourceQueryService;
    private final StorageQueryService storageQueryService;
    private final StorageSettingCommandService storageSettingCommandService;
    private final StorageSettingQueryService storageSettingQueryService;

    public void create(UUID storageSettingId, UpdateStorageSettingsRequest request) {
        StorageSetting storageSetting = storageSettingQueryService.findByStorageSettingIdAndPlayerIdValidated(storageSettingId);
        Reservation reservation = reservationQueryService.findByExternalReferenceAndDataIdAndPlayerId(storageSettingId, storageSetting.getDataId())
            .orElseGet(() -> createDefault(storageSetting));

        ResourceData resourceData = resourceDataService.getOptional(storageSetting.getDataId())
            .orElseThrow(() -> ExceptionFactory.dataNotFound(storageSetting.getDataId()));
        int availableStoragePlace = storageQueryService.getAvailableStoragePlace(storageSetting.getStarId(), resourceData.getStorageType()) + reservation.getAmount();
        int actualAmount = resourceQueryService.findLatestAmountByStarIdAndDataIdAndPlayerId(storageSetting.getStarId(), storageSetting.getDataId());
        int reserve = request.getTargetAmount() - actualAmount;

        if (availableStoragePlace < reserve) {
            throw ExceptionFactory.storageFull(storageSetting.getStarId(), resourceData.getStorageType());
        }

        reservation.setAmount(Math.max(reserve, 0));
        reservationCommandService.save(reservation);

        storageSetting.setTargetAmount(request.getTargetAmount());
        storageSetting.setPriority(request.getPriority());
        storageSettingCommandService.save(storageSetting);
    }

    private Reservation createDefault(StorageSetting storageSetting) {
        return reservationFactory.create(
            storageSetting.getGameId(),
            storageSetting.getStarId(),
            storageSetting.getDataId(),
            0,
            ReservationType.STORAGE_SETTING,
            storageSetting.getStorageSettingId(),
            storageSetting.getPlayerId()
        );
    }
}
