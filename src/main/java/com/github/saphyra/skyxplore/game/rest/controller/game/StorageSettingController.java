package com.github.saphyra.skyxplore.game.rest.controller.game;

import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.rest.request.CreateStorageSettingRequest;
import com.github.saphyra.skyxplore.game.rest.request.UpdateStorageSettingsRequest;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingCreationDetailsView;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingView;
import com.github.saphyra.skyxplore.game.rest.view.storage.StorageSettingViewConverter;
import com.github.saphyra.skyxplore.game.service.system.storage.setting.StorageSettingCreationDetailsViewQueryService;
import com.github.saphyra.skyxplore.game.service.system.storage.setting.creation.StorageSettingCreationService;
import com.github.saphyra.skyxplore.game.service.system.storage.setting.update.StorageSettingUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StorageSettingController {
    private static final String CREATE_STORAGE_SETTINGS_MAPPING = API_PREFIX + "/game/star/{starId}/system/storage-settings";
    private static final String GET_STORAGE_SETTINGS_MAPPING = API_PREFIX + "/game/star/{starId}/system/storage-settings";
    private static final String GET_STORAGE_SETTINGS_CREATION_DETAILS_MAPPING = API_PREFIX + "/game/star/{starId}/system/storage-settings/creation-details";
    private static final String UPDATE_STORAGE_SETTINGS_MAPPING = API_PREFIX + "/game/star/system/storage-settings/{storageSettingId}";

    private final StorageSettingCreationDetailsViewQueryService storageSettingCreationDetailsViewQueryService;
    private final StorageSettingCreationService storageSettingCreationService;
    private final StorageSettingQueryService storageSettingQueryService;
    private final StorageSettingUpdateService storageSettingUpdateService;
    private final StorageSettingViewConverter storageSettingViewConverter;

    @PutMapping(CREATE_STORAGE_SETTINGS_MAPPING)
    void createStorageSettings(
        @PathVariable("starId") UUID starId,
        @RequestBody @Valid CreateStorageSettingRequest request
    ) {
        log.info("Creating StorageSetting for starId {} and details {}", starId, request);
        storageSettingCreationService.create(starId, request);
    }

    @GetMapping(GET_STORAGE_SETTINGS_MAPPING)
    List<StorageSettingView> getStorageSettings(@PathVariable("starId") UUID starId) {
        return storageSettingViewConverter.convertDomain(storageSettingQueryService.getByStarIdAndPlayerId(starId));
    }

    @GetMapping(GET_STORAGE_SETTINGS_CREATION_DETAILS_MAPPING)
    StorageSettingCreationDetailsView getStorageSettingCreationDetailsView(@PathVariable("starId") UUID starId){
        return storageSettingCreationDetailsViewQueryService.getStorageCreationDetails(starId);
    }

    @PostMapping(UPDATE_STORAGE_SETTINGS_MAPPING)
    void updateStorageSettings(
        @PathVariable("storageSettingId") UUID storageSettingsd,
        @RequestBody @Valid UpdateStorageSettingsRequest request
    ) {
        log.info("Update StorageSetting for storageSettingId {} and details {}", storageSettingsd, request);
        storageSettingUpdateService.create(storageSettingsd, request);
    }
}
