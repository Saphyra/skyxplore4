package com.github.saphyra.skyxplore.game.rest.controller.game;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.rest.view.MapView;
import com.github.saphyra.skyxplore.game.rest.view.connection.StarConnectionView;
import com.github.saphyra.skyxplore.game.service.map.connection.VisibleStarConnectionQueryService;
import com.github.saphyra.skyxplore.game.service.map.star.StarQueryService;
import com.github.saphyra.skyxplore.game.rest.view.star.StarMapView;
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

    private final VisibleStarConnectionQueryService visibleStarConnectionQueryService;
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
            .connections(getConnections(visibleStars))
            .build();
    }

    private List<StarConnectionView> getConnections(List<StarMapView> visibleStars) {
        List<UUID> visibleStarIds = visibleStars.stream()
            .map(StarMapView::getStarId)
            .collect(Collectors.toList());
        return visibleStarConnectionQueryService.getVisibleByGameIdAndUserId(visibleStarIds);
    }
}
