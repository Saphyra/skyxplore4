package com.github.saphyra.skyxplore.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.INDEX_MAPPING;

@Controller
@RequiredArgsConstructor
@Slf4j
class PageController {

    @GetMapping(INDEX_MAPPING)
    String index() {
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }
}
