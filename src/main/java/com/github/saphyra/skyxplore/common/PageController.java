package com.github.saphyra.skyxplore.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.github.saphyra.skyxplore.common.RequestConstants.WEB_PREFIX;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    public static final String INDEX_MAPPING = WEB_PREFIX;
    public static final String MAIN_MENU_MAPPING = WEB_PREFIX + "/main-menu";

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
}
