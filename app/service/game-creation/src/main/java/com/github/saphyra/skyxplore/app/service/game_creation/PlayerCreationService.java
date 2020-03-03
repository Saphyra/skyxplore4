package com.github.saphyra.skyxplore.app.service.game_creation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.auth.UserQueryService;
import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.data.name.FirstNames;
import com.github.saphyra.skyxplore.app.domain.data.name.LastNames;
import com.github.saphyra.skyxplore.app.domain.player.Player;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
//TODO unit test
public class PlayerCreationService {
    private final DomainSaverService domainSaverService;
    private final FirstNames firstNames;
    private final IdGenerator idGenerator;
    private final LastNames lastNames;
    private final Random random;
    private final UserQueryService userQueryService;


    public Player create(UUID gameId, UUID userId, boolean isAi, List<String> usedPlayerNames) {
        Player player = Player.builder()
            .playerId(idGenerator.randomUUID())
            .gameId(gameId)
            .ai(isAi)
            .playerName(isAi ? generateName(usedPlayerNames) : userQueryService.findByUserIdValidated(userId).getCredentials().getUserName())
            .isNew(true)
            .build();

        domainSaverService.add(player);
        log.debug("Player created: {}", player);
        return player;
    }

    private String generateName(List<String> usedPlayerNames) {
        String result;
        do {
            result = lastNames.get(random.randInt(0, lastNames.size() - 1)) + " " + firstNames.get(random.randInt(0, firstNames.size() - 1));
        } while (usedPlayerNames.contains(result));
        log.debug("playerName generated: {}", result);
        return result;
    }
}
