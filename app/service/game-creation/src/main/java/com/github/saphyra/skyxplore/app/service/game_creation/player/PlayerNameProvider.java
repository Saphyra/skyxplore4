package com.github.saphyra.skyxplore.app.service.game_creation.player;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.auth.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class PlayerNameProvider {
    private final AiNameGenerator aiNameGenerator;
    private final UserQueryService userQueryService;

    String getPlayerName(boolean isAi, UUID userId, List<String> usedPlayerNames) {
        return isAi ? aiNameGenerator.generateName(usedPlayerNames) : userQueryService.findByUserIdValidated(userId).getCredentials().getUserName();
    }
}
