package com.github.saphyra.skyxplore.app.service.game_creation.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.common.utils.Mapping;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlayerCreationService {
    private final DomainSaverService domainSaverService;
    private final PlayerFactory playerFactory;

    public List<Mapping<Coordinate, UUID>> create(UUID gameId, UUID userId, List<Coordinate> coordinates) {
        List<String> usedPlayerNames = new ArrayList<>();
        boolean isAi = false;
        List<Mapping<Coordinate, UUID>> result = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            Mapping<Coordinate, UUID> mapping = Mapping.<Coordinate, UUID>builder()
                .key(coordinate)
                .value(create(gameId, userId, isAi, usedPlayerNames))
                .build();
            isAi = true;
            result.add(mapping);
        }
        log.debug("CoordinatePlayerMapping: {}", result);
        return result;
    }

    private UUID create(UUID gameId, UUID userId, boolean isAi, List<String> usedPlayerNames) {
        Player player = playerFactory.create(gameId, userId, isAi, usedPlayerNames);
        usedPlayerNames.add(player.getPlayerName());

        domainSaverService.add(player);
        log.debug("Player created: {}", player);
        return player.getPlayerId();
    }
}
