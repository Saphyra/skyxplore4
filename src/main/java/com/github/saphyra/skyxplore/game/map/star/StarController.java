package com.github.saphyra.skyxplore.game.map.star;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.map.star.view.StarMapView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StarController {
    private static final String GET_STAR_MAPPING = API_PREFIX + "/game/star/{starId}";

    private final StarQueryService starQueryService;

    @GetMapping(GET_STAR_MAPPING)
    StarMapView getStar(
        @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId,
        @PathVariable("starId") UUID starId)
    {
        log.info("{} wants to know the detail of star {}", playerId, starId);
        return starQueryService.findDetailsOfStar(playerId, starId);
    }
}
