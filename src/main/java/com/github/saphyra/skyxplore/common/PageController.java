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
    public static final String INDEX_MAPPING = WEB_PREFIX ;

    @GetMapping(INDEX_MAPPING)
    String index() {
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }
}
