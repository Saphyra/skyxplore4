package com.github.saphyra.skyxplore.app.service.game_creation.player;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.data.name.FirstNames;
import com.github.saphyra.skyxplore.app.domain.data.name.LastNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class AiNameGenerator {
    private final FirstNames firstNames;
    private final LastNames lastNames;

    String generateName(List<String> usedPlayerNames) {
        String result;
        do {
            result = lastNames.getRandom() + " " + firstNames.getRandom();
        } while (usedPlayerNames.contains(result));
        log.debug("playerName generated: {}", result);
        return result;
    }
}
