package com.github.saphyra.skyxplore.game.common.controller.game;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.common.view.MapView;
import com.github.saphyra.skyxplore.game.map.connection.StarConnectionQueryService;
import com.github.saphyra.skyxplore.game.map.star.StarQueryService;
import com.github.saphyra.skyxplore.game.map.star.view.StarMapView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapViewController {
    private static final String GET_STARS_MAPPING = API_PREFIX + "/game/map";

    private final StarConnectionQueryService starConnectionQueryService;
    private final StarQueryService starQueryService;

    @GetMapping(GET_STARS_MAPPING)
    MapView getMap(
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
        @CookieValue(RequestConstants.COOKIE_GAME_ID) UUID gameId,
        @CookieValue(RequestConstants.COOKIE_PLAYER_ID) UUID playerId
    ) {
        log.info("{} wants to know the stars belong to game {}", userId, gameId);
        List<StarMapView> visibleStars = starQueryService.getVisibleStars(gameId, userId, playerId);
        return MapView.builder()
            .stars(visibleStars)
            .connections(starConnectionQueryService.getVisibleByGameIdAndUserId(gameId, userId, visibleStars.stream().map(StarMapView::getStarId).collect(Collectors.toList())))
            .build();
    }
}
