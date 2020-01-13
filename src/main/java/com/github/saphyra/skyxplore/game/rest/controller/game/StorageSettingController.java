package com.github.saphyra.skyxplore.game.rest.controller.game;

import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingView;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingViewConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
public class StorageSettingController {
    private static final String GET_STORAGE_SETTINGS_MAPPING = API_PREFIX + "/game/star/{starId}/system/storage-settings";

    private final StorageSettingQueryService storageSettingQueryService;
    private final StorageSettingViewConverter storageSettingViewConverter;

    @GetMapping(GET_STORAGE_SETTINGS_MAPPING)
    List<StorageSettingView> getStorageSettings(@PathVariable("starId") UUID starId) {
        return storageSettingViewConverter.convertDomain(storageSettingQueryService.getByStarIdAndPlayerId(starId));
    }
}
