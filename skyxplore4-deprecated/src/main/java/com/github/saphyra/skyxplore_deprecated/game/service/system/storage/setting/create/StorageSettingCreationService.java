package com.github.saphyra.skyxplore_deprecated.game.service.system.storage.setting.create;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting.StorageSettingCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore_deprecated.game.rest.request.CreateStorageSettingRequest;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.StorageQueryService;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageSettingCreationService {
    private final RequestContextHolder requestContextHolder;
    private final ReservationService reservationService;
    private final ResourceDataService resourceDataService;
    private final ResourceQueryService resourceQueryService;
    private final StorageBuildingService storageBuildingService;
    private final StorageSettingFactory storageSettingFactory;
    private final StorageQueryService storageQueryService;
    private final StorageSettingCommandService storageSettingCommandService;
    private final StorageSettingQueryService storageSettingQueryService;

    public void create(UUID starId, CreateStorageSettingRequest request) {
        if (storageSettingQueryService.getByStarIdAndDataIdAndPlayerId(starId, request.getDataId()).isPresent()) {
            throw ExceptionFactory.storageSettingAlreadyExists(starId, request.getDataId());
        }

        ResourceData resourceData = resourceDataService.getOptional(request.getDataId())
            .orElseThrow(() -> ExceptionFactory.dataNotFound(request.getDataId()));
        StorageBuilding storageBuilding = storageBuildingService.findByStorageType(resourceData.getStorageType());

        int availableStoragePlace = storageQueryService.getAvailableStoragePlace(starId, storageBuilding.getStores());
        int actualAmount = resourceQueryService.findLatestAmountByStarIdAndDataIdAndPlayerId(starId, request.getDataId());
        int reserve = request.getTargetAmount() - actualAmount;
        if (availableStoragePlace < reserve) {
            throw ExceptionFactory.storageFull(starId, storageBuilding.getStores());
        }

        RequestContext requestContext = requestContextHolder.get();
        UUID gameId = requestContext.getGameId();
        UUID playerId = requestContext.getPlayerId();
        StorageSetting storageSetting = storageSettingFactory.create(
            gameId,
            starId,
            playerId,
            request.getDataId(),
            request.getTargetAmount(),
            request.getPriority(),
            request.getBatchSize()
        );
        storageSettingCommandService.save(storageSetting);
        reservationService.reserve(
            gameId,
            starId,
            request.getDataId(),
            reserve,
            storageBuilding.getStores(),
            ReservationType.STORAGE_SETTING,
            storageSetting.getStorageSettingId(),
            playerId
        );
    }
}
