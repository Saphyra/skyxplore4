package com.github.saphyra.skyxplore.game.service.system.building;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_GAME_ID;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_PLAYER_ID;

import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.game.service.system.building.build.BuildNewBuildingService;
import com.github.saphyra.skyxplore.game.service.system.building.upgrade.UpgradeBuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BuildingController {
    private static final String BUILD_NEW_BUILDING_MAPPING = API_PREFIX + "/game/building/{surfaceId}";
    private static final String UPGRADE_BUILDING_MAPPING = API_PREFIX + "/game/building/{buildingId}/upgrade";

    private final BuildNewBuildingService buildNewBuildingService;
    private final UpgradeBuildingService upgradeBuildingService;

    @PostMapping(BUILD_NEW_BUILDING_MAPPING)
    void buildNewBuilding(
        @PathVariable("surfaceId") UUID surfaceId,
        @CookieValue(COOKIE_GAME_ID) UUID gameId,
        @CookieValue(COOKIE_PLAYER_ID) UUID playerId,
        @RequestBody OneStringParamRequest dataId
    ) {
        log.info("{} wants to build a {} on surface {}", playerId, dataId.getValue(), surfaceId);
        buildNewBuildingService.buildNewBuilding(gameId, surfaceId, dataId.getValue());
    }

    @PostMapping(UPGRADE_BUILDING_MAPPING)
    void upgradeBuilding(
        @PathVariable("buildingId") UUID buildingId,
        @CookieValue(COOKIE_GAME_ID) UUID gameId,
        @CookieValue(COOKIE_PLAYER_ID) UUID playerId
    ) {
        log.info("{} wants to upgrade building", playerId, buildingId);
        upgradeBuildingService.upgrade(gameId, buildingId);
    }
}
