package com.github.saphyra.skyxplore.web;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.GAME_MAPPING;
import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.INDEX_MAPPING;
import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.MAIN_MENU_MAPPING;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.app.domain.game.GameQueryService;
import com.github.saphyra.skyxplore.app.domain.player.PlayerQueryService;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
class PageController {
    private final CookieUtil cookieUtil;
    private final GameQueryService gameQueryService;
    private final PlayerQueryService playerQueryService;

    @GetMapping(INDEX_MAPPING)
    String index() {
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }

    @GetMapping(MAIN_MENU_MAPPING)
    String mainMenu(HttpServletResponse response) {
        log.info("Request arrived to {}", MAIN_MENU_MAPPING);
        cookieUtil.setCookie(response, RequestConstants.COOKIE_PLAYER_ID, "", 0);
        cookieUtil.setCookie(response, RequestConstants.COOKIE_GAME_ID, "", 0);
        return "main_menu";
    }

    @GetMapping(GAME_MAPPING)
    String selectGame(
        @PathVariable("gameId") UUID gameId,
        HttpServletResponse response
    ) {
        if (gameQueryService.findByGameIdAndUserId(gameId).isPresent()) {
            cookieUtil.setCookie(response, RequestConstants.COOKIE_GAME_ID, gameId.toString());
            cookieUtil.setCookie(response, RequestConstants.COOKIE_PLAYER_ID, playerQueryService.findPlayerIdByUserIdAndGameId(gameId).toString());
            return "game";
        } else {
            return String.format("redirect:%s", MAIN_MENU_MAPPING);
        }
    }
}
