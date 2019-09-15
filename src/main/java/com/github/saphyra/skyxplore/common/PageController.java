package com.github.saphyra.skyxplore.common;

import com.github.saphyra.skyxplore.game.game.domain.EncryptionGameRepository;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.WEB_PREFIX;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    public static final String GAME_MAPPING = WEB_PREFIX + "/game";
    public static final String INDEX_MAPPING = WEB_PREFIX;
    public static final String MAIN_MENU_MAPPING = WEB_PREFIX + "/main-menu";
    private static final String SELECT_GAME_MAPPING = GAME_MAPPING + "/{gameId}";

    private final CookieUtil cookieUtil;
    private final EncryptionGameRepository gameRepository;

    @GetMapping(GAME_MAPPING)
    String game(){
        log.info("Request arrived to {}", GAME_MAPPING);
        return "game";
    }

    @GetMapping(INDEX_MAPPING)
    String index() {
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }

    @GetMapping(MAIN_MENU_MAPPING)
    String mainMenu() {
        log.info("Request arrived to {}", MAIN_MENU_MAPPING);
        return "main_menu";
    }

    @GetMapping(SELECT_GAME_MAPPING)
    String selectGame(
        @PathVariable("gameId") UUID gameId,
        @CookieValue(RequestConstants.COOKIE_USER_ID) UUID userId,
        HttpServletResponse response
    ){
        log.info("{} wants to select game {}", userId, gameId);
        String redirection = gameRepository.findByGameIdAndUserId(gameId, userId)
            .map(game -> PageController.GAME_MAPPING)
            .map(s -> {
                cookieUtil.setCookie(response, RequestConstants.COOKIE_GAME_ID, gameId.toString());
                return s;
            })
            .orElse(PageController.MAIN_MENU_MAPPING);
        log.info("Redirecting to: {}", redirection);
        return String.format("redirect:%s", redirection);
    }
}
