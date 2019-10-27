package com.github.saphyra.skyxplore.game.map.surface;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceDao;
import com.github.saphyra.skyxplore.game.common.view.surface.SurfaceView;
import com.github.saphyra.skyxplore.game.common.view.surface.SurfaceViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SurfaceController {
    private static final String GET_SURFACES_OF_STAR_MAPPING = API_PREFIX + "/game/star/{starId}/surface";

    private final SurfaceDao surfaceDao;
    private final SurfaceViewConverter surfaceViewConverter;

    @GetMapping(GET_SURFACES_OF_STAR_MAPPING)
    List<SurfaceView> getSurfacesOfStar(
        @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
        @PathVariable("starId") UUID starId
    ) {
        log.info("Player {} wants to know the surfaces of star {}. userId: {}, gameId: {}", playerId, starId);
        return surfaceViewConverter.convertDomain(surfaceDao.getByStarId(starId));
    }
}
