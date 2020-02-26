package com.github.saphyra.skyxplore_deprecated.game.rest.controller;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.COOKIE_PLAYER_ID;

import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PlayerController {
    private static final String GET_PLAYER_ID_MAPPING = API_PREFIX + "/game/player/id";

    private final UuidConverter uuidConverter;

    @GetMapping(GET_PLAYER_ID_MAPPING)
    String getPlayerId(
        @CookieValue(COOKIE_PLAYER_ID) UUID playerId
    ) {
        return uuidConverter.convertDomain(playerId);
    }
}
