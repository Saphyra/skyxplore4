package com.github.saphyra.skyxplore_deprecated.common;

import com.github.saphyra.skyxplore_deprecated.game.dao.game.GameQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.player.PlayerQueryService;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.WEB_PREFIX;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    private static final String GAME_MAPPING = WEB_PREFIX + "/game";
    public static final String INDEX_MAPPING = WEB_PREFIX;
    private static final String MAIN_MENU_MAPPING = WEB_PREFIX + "/main-menu";
    private static final String SELECT_GAME_MAPPING = GAME_MAPPING + "/{gameId}";

    private final CookieUtil cookieUtil;
    private final GameQueryService gameQueryService;
    private final PlayerQueryService playerQueryService;

    @GetMapping(GAME_MAPPING)
    String game() {
        log.info("Request arrived to {}", GAME_MAPPING);
        return "game";
    }

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

    @GetMapping(SELECT_GAME_MAPPING)
    String selectGame(
        @PathVariable("gameId") UUID gameId,
        HttpServletResponse response
    ) {
        String redirection;
        if (gameQueryService.findByGameIdAndUserId(gameId).isPresent()) {
            redirection = PageController.GAME_MAPPING;
            cookieUtil.setCookie(response, RequestConstants.COOKIE_GAME_ID, gameId.toString());
            cookieUtil.setCookie(response, RequestConstants.COOKIE_PLAYER_ID, playerQueryService.findPlayerIdByUserIdAndGameId(gameId).toString());
        } else {
            redirection = PageController.MAIN_MENU_MAPPING;
        }
        log.info("Redirecting to: {}", redirection);
        return String.format("redirect:%s", redirection);
    }
}
