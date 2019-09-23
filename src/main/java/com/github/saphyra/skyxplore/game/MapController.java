package com.github.saphyra.skyxplore.game;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.common.view.MapView;
import com.github.saphyra.skyxplore.game.map.connection.StarConnectionQueryService;
import com.github.saphyra.skyxplore.game.map.star.StarQueryService;
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
        return MapView.builder()
            .stars(starQueryService.getByGameIdAndUserId(gameId, userId))
            .connections(starConnectionQueryService.getByGameIdAndUserId(gameId, userId))
            .build();
    }
}
