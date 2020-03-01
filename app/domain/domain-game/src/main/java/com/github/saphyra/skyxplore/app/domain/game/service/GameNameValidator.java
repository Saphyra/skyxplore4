package com.github.saphyra.skyxplore.app.domain.game.service;

import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class GameNameValidator {
    private static final String FIELD_NAME = "gameName";

    void validate(String gameName) {
        if (isBlank(gameName)) {
            throw ExceptionFactory.invalidRequest(FIELD_NAME, "GameName is null or empty.");
        }

        if (gameName.length() < 3) {
            throw ExceptionFactory.invalidRequest(FIELD_NAME, "GameName is too short.");
        }

        if (gameName.length() > 30) {
            throw ExceptionFactory.invalidRequest(FIELD_NAME, "GameName is too long.");
        }
    }
}
