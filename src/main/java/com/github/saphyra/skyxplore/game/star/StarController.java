package com.github.saphyra.skyxplore.game.star;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.star.view.StarView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StarController {
    private static final String GET_STARS_MAPPING = API_PREFIX + "/game/star";

    private final StarQueryService starQueryService;

    @GetMapping(GET_STARS_MAPPING)
    List<StarView> getStars(
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
        @CookieValue(RequestConstants.COOKIE_GAME_ID) UUID gameId
    ) {
        log.info("{} wants to know the stars belong to game {}", userId, gameId);
        return starQueryService.getByGameIdAndUserId(gameId, userId);
    }
}
