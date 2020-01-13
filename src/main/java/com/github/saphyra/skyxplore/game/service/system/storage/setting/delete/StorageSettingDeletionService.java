package com.github.saphyra.skyxplore.game.service.system.storage.setting.delete;

import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageSettingDeletionService {
    private final ReservationCommandService reservationCommandService;
    private final StorageSettingCommandService storageSettingCommandService;
    private final StorageSettingQueryService storageSettingQueryService;

    public void delete(UUID storageSettingId) {
        StorageSetting storageSetting = storageSettingQueryService.findByStorageSettingIdAndPlayerIdValidated(storageSettingId);
        reservationCommandService.deleteByExternalReferenceAndPlayerId(storageSettingId);
        storageSettingCommandService.delete(storageSetting);
    }
}
