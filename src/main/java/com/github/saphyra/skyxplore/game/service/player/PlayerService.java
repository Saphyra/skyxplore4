package com.github.saphyra.skyxplore.game.service.player;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.data.gamedata.FirstNames;
import com.github.saphyra.skyxplore.data.gamedata.LastNames;
import com.github.saphyra.skyxplore.game.dao.player.Player;
import com.github.saphyra.skyxplore.game.dao.player.PlayerDao;
import com.github.saphyra.skyxplore.platform.auth.UserQueryService;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlayerService {
    private final FirstNames firstNames;
    private final IdGenerator idGenerator;
    private final LastNames lastNames;
    private final PlayerDao playerDao;
    private final Random random;
    private final UserQueryService userQueryService;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        log.debug("Deleting players related to game {}", event);
        playerDao.gameDeletedEventListener(event.getGameId(), event.getUserId());
    }

    public Player create(UUID gameId, UUID userId, boolean isAi, List<String> usedPlayerNames) {
        Player player = Player.builder()
            .playerId(idGenerator.randomUUID())
            .gameId(gameId)
            .userId(userId)
            .ai(isAi)
            .playerName(isAi ? generateName(usedPlayerNames) : userQueryService.findByUserIdValidated(userId).getCredentials().getUserName())
            .build();

        playerDao.save(player);
        log.debug("Player created: {}", player);
        return player;
    }

    private String generateName(List<String> usedPlayerNames) {
        String result;
         do{
             result = lastNames.get(random.randInt(0, lastNames.size()-1)) + " " + firstNames.get(random.randInt(0, firstNames.size()-1));
         }while (usedPlayerNames.contains(result));
        log.debug("playerName generated: {}", result);
         return result;
    }
}