package com.github.saphyra.skyxplore_deprecated.game.rest.controller.game;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore_deprecated.common.RequestConstants;
import com.github.saphyra.skyxplore_deprecated.game.common.StarSystemDetailsQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.StarQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.star.StarMapViewConverter;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.surface.SurfaceView;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.surface.SurfaceViewConverter;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.StarSystemDetailsView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StarViewController {
    private static final String GET_STAR_SYSTEM_DETAILS_MAPPING = API_PREFIX + "/game/star/{starId}/system/details";
    private static final String GET_SURFACES_OF_STAR_MAPPING = API_PREFIX + "/game/star/{starId}/surface";

    private final StarMapViewConverter starMapViewConverter;
    private final StarQueryService starQueryService;
    private final StarSystemDetailsQueryService starSystemDetailsQueryService;
    private final SurfaceQueryService surfaceQueryService;
    private final SurfaceViewConverter surfaceViewConverter;

    @GetMapping(GET_STAR_SYSTEM_DETAILS_MAPPING)
    StarSystemDetailsView getStarSystemDetails(
        @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
        @PathVariable("starId") UUID starId
    ) {
        log.info("{} wants to know the details of star {}", playerId, starId);
        return starSystemDetailsQueryService.getDetailsOfStarSystem(starId);
    }

    @GetMapping(GET_SURFACES_OF_STAR_MAPPING)
    List<SurfaceView> getSurfacesOfStar(
        @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
        @PathVariable("starId") UUID starId
    ) {
        log.info("Player {} wants to know the surfaces of star {}", playerId, starId);
        return surfaceViewConverter.convertDomain(surfaceQueryService.getByStarIdAndPlayerId(starId));
    }
}
