package com.github.saphyra.skyxplore.game.map.connection;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnection;
import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnectionDao;
import com.github.saphyra.skyxplore.game.map.connection.view.StarConnectionView;
import com.github.saphyra.skyxplore.game.map.connection.view.StarConnectionViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarConnectionQueryService {
    private final StarConnectionDao starConnectionDao;
    private final StarConnectionViewConverter starConnectionViewConverter;

    public List<StarConnectionView> getVisibleByGameIdAndUserId(UUID gameId, UUID userId, List<UUID> visibleStarIds) {
        List<StarConnection> visibleConnections = starConnectionDao.getByGameIdAndUserId(gameId, userId).stream()
            .filter(starConnection -> isVisible(starConnection, visibleStarIds))
            .collect(Collectors.toList());
        return starConnectionViewConverter.convertDomain(visibleConnections);
    }

    private boolean isVisible(StarConnection starConnection, List<UUID> visibleStarIds) {
        return visibleStarIds.contains(starConnection.getStar1()) || visibleStarIds.contains(starConnection.getStar2());
    }
}
