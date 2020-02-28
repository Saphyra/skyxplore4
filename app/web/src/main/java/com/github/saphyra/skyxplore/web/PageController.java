package com.github.saphyra.skyxplore.web;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.INDEX_MAPPING;
import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.WEB_PREFIX;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class PageController {
    private static final String MAIN_MENU_MAPPING = WEB_PREFIX + "/main-menu";

    private final CookieUtil cookieUtil;

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
}
