package com.github.saphyra.skyxplore.game.service.system.storage.setting.update;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.rest.request.UpdateStorageSettingsRequest;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageSettingUpdateService {
    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;
    private final ResourceQueryService resourceQueryService;
    private final StorageBuildingService storageBuildingService;
    private final StorageQueryService storageQueryService;
    private final StorageSettingCommandService storageSettingCommandService;
    private final StorageSettingQueryService storageSettingQueryService;

    public void create(UUID storageSettingId, UpdateStorageSettingsRequest request) {
        StorageSetting storageSetting = storageSettingQueryService.getByStorageSettingIdAndPlayerIdValidated(storageSettingId);
        Reservation reservation = reservationQueryService.findByExternalReferenceAndDataIdAndPlayerIdValidated(storageSettingId, storageSetting.getDataId());

        StorageBuilding storageBuilding = storageBuildingService.getOptional(storageSetting.getDataId())
            .orElseThrow(() -> ExceptionFactory.dataNotFound(storageSetting.getDataId()));
        int availableStoragePlace = storageQueryService.getAvailableStoragePlace(storageSetting.getStarId(), storageBuilding.getStores()) + reservation.getAmount();
        int actualAmount = resourceQueryService.findLatestAmountByStarIdAndDataIdAndPlayerId(storageSetting.getStarId(), storageSetting.getDataId());
        int reserve = request.getTargetAmount() - actualAmount;

        if (availableStoragePlace < reserve) {
            throw ExceptionFactory.storageFull(storageSetting.getStarId(), storageBuilding.getStores());
        }

        reservation.setAmount(reserve);
        reservationCommandService.save(reservation);

        storageSetting.setTargetAmount(request.getTargetAmount());
        storageSetting.setPriority(request.getPriority());
        storageSettingCommandService.save(storageSetting);
    }
}
