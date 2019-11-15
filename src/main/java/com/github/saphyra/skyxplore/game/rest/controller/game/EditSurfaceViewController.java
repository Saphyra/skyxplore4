package com.github.saphyra.skyxplore.game.rest.controller.game;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_GAME_ID;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_PLAYER_ID;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.game.service.map.surface.EditSurfaceQueryService;
import com.github.saphyra.skyxplore.game.service.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.rest.view.surface.BuildableBuildingView;
import com.github.saphyra.skyxplore.game.rest.view.surface.SurfaceView;
import com.github.saphyra.skyxplore.game.rest.view.surface.SurfaceViewConverter;
import com.github.saphyra.skyxplore.game.rest.view.surface.TerraformingPossibilityView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EditSurfaceViewController {
    private static final String BUILD_NEW_BUILDING_MAPPING = API_PREFIX + "/game/building/{surfaceId}";
    private static final String GET_BUILDABLE_BUILDINGS_MAPPING = API_PREFIX + "/data/building/{surfaceId}";
    private static final String GET_SURFACE_DETAILS_MAPPING = API_PREFIX + "/game/surface/{surfaceId}";
    private static final String GET_TERRAFORMING_POSSIBILITIES = API_PREFIX + "/game/surface/{surfaceId}/terraform";

    private final SurfaceQueryService surfaceQueryService;
    private final SurfaceViewConverter surfaceViewConverter;
    private final EditSurfaceQueryService editSurfaceQueryService;

    @PostMapping(BUILD_NEW_BUILDING_MAPPING)
    void buildNewBuilding(
        @PathVariable("surfaceId") UUID surfaceId,
        @CookieValue(COOKIE_GAME_ID) UUID gameId,
        @CookieValue(COOKIE_PLAYER_ID) UUID playerId,
        @RequestBody OneStringParamRequest dataId
    ) {
        log.info("{} wants to build a {} on surface {}", playerId, dataId.getValue(), surfaceId);
        //TODO implement
    }

    @GetMapping(GET_BUILDABLE_BUILDINGS_MAPPING)
    List<BuildableBuildingView> getBuildableBuildings(
            @PathVariable("surfaceId") UUID surfaceId
    ) {
        log.info("Querying buildable buildings for surfaceId {}", surfaceId);
        return editSurfaceQueryService.getBuildableBuildings(surfaceId);
    }

    @GetMapping(GET_SURFACE_DETAILS_MAPPING)
    SurfaceView getSurfaceDetails(@PathVariable("surfaceId") UUID surfaceId) {
        log.info("Querying surface with surfaceId {}", surfaceId);
        return surfaceViewConverter.convertDomain(surfaceQueryService.findBySurfaceId(surfaceId));
    }

    @GetMapping(GET_TERRAFORMING_POSSIBILITIES)
    List<TerraformingPossibilityView> getTerraformingPossibilities(@PathVariable("surfaceId") UUID surfaceId){
        log.info("Querying terraforming possibilities of surface {}", surfaceId);
        return editSurfaceQueryService.getTerraformingPossibilities(surfaceId);
    }
}
