package com.github.saphyra.skyxplore.game;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.common.view.MapView;
import com.github.saphyra.skyxplore.game.map.connection.StarConnectionQueryService;
import com.github.saphyra.skyxplore.game.map.star.StarQueryService;
import com.github.saphyra.skyxplore.game.map.star.view.StarView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapController {
    private static final String GET_STARS_MAPPING = API_PREFIX + "/game/map";

    private final StarConnectionQueryService starConnectionQueryService;
    private final StarQueryService starQueryService;

    @GetMapping(GET_STARS_MAPPING)
    MapView getMap(
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
        @CookieValue(RequestConstants.COOKIE_GAME_ID) UUID gameId
    ) {
        log.info("{} wants to know the stars belong to game {}", userId, gameId);
        List<StarView> visibleStars = starQueryService.getVisibleByGameIdAndUserId(gameId, userId);
        return MapView.builder()
            .stars(visibleStars)
            .connections(starConnectionQueryService.getVisibleByGameIdAndUserId(gameId, userId, visibleStars.stream().map(StarView::getStarId).collect(Collectors.toList())))
            .build();
    }
}
