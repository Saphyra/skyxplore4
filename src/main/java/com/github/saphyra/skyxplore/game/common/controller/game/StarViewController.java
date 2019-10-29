package com.github.saphyra.skyxplore.game.common.controller.game;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.common.view.surface.SurfaceView;
import com.github.saphyra.skyxplore.game.common.view.surface.SurfaceViewConverter;
import com.github.saphyra.skyxplore.game.map.star.StarQueryService;
import com.github.saphyra.skyxplore.game.map.star.StarSystemDetailsQueryService;
import com.github.saphyra.skyxplore.game.map.star.view.StarDetailsView;
import com.github.saphyra.skyxplore.game.map.star.view.StarMapView;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StarViewController {
    private static final String GET_STAR_MAPPING = API_PREFIX + "/game/star/{starId}";
    private static final String GET_STAR_SYSTEM_DETAILS_MAPPING = API_PREFIX + "/game/star/{starId}/system/details";
    private static final String GET_SURFACES_OF_STAR_MAPPING = API_PREFIX + "/game/star/{starId}/surface";

    private final StarQueryService starQueryService;
    private final StarSystemDetailsQueryService starSystemDetailsQueryService;
    private final SurfaceDao surfaceDao;
    private final SurfaceViewConverter surfaceViewConverter;

    @GetMapping(GET_STAR_MAPPING)
    StarMapView getStar(
            @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
            @PathVariable("starId") UUID starId) {
        log.info("{} wants to know the detail of star {}", playerId, starId);
        return starQueryService.findDetailsOfStar(playerId, starId);
    }

    @GetMapping(GET_STAR_SYSTEM_DETAILS_MAPPING)
    StarDetailsView getStarSystemDetails(
            @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
            @PathVariable("starId") UUID starId) {
        log.info("{} wants to know the details of star {}", playerId, starId);
        return starSystemDetailsQueryService.getDetailsOfStarSystem(starId, playerId);
    }

    @GetMapping(GET_SURFACES_OF_STAR_MAPPING)
    List<SurfaceView> getSurfacesOfStar(
            @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
            @PathVariable("starId") UUID starId
    ) {
        log.info("Player {} wants to know the surfaces of star {}", playerId, starId);
        return surfaceViewConverter.convertDomain(surfaceDao.getByStarId(starId));
    }
}
