package com.github.saphyra.skyxplore.platform.context;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.UUID;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
class RequestContextFactory {
    private final CookieUtil cookieUtil;

    RequestContext createContext(HttpServletRequest request) {
        return RequestContext.builder()
                .gameId(fetch(request, RequestConstants.COOKIE_GAME_ID))
                .userId(fetch(request, RequestConstants.COOKIE_USER_ID))
                .playerId(fetch(request, RequestConstants.COOKIE_PLAYER_ID))
                .build();
    }

    private UUID fetch(HttpServletRequest request, String name) {
        return cookieUtil.getCookie(request, name)
                .filter(s -> !isNullOrEmpty(s))
                .map(UUID::fromString)
                .orElseGet(() -> {
                    log.debug("Cookie is empty with name {}", name);
                    return null;
                });
    }
}
