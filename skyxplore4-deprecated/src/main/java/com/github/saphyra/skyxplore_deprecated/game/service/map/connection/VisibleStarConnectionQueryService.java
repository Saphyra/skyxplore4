package com.github.saphyra.skyxplore_deprecated.game.service.map.connection;

import com.github.saphyra.skyxplore_deprecated.game.dao.map.connection.StarConnection;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.connection.StarConnectionQueryService;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.connection.StarConnectionView;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.connection.StarConnectionViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisibleStarConnectionQueryService {
    private final StarConnectionQueryService starConnectionQueryService;
    private final StarConnectionViewConverter starConnectionViewConverter;

    public List<StarConnectionView> getVisibleByStars(List<UUID> visibleStarIds) {
        List<StarConnection> visibleConnections = starConnectionQueryService.getByGameIdAndUserId().stream()
            .filter(starConnection -> isVisible(starConnection, visibleStarIds))
            .collect(Collectors.toList());
        return starConnectionViewConverter.convertDomain(visibleConnections);
    }

    private boolean isVisible(StarConnection starConnection, List<UUID> visibleStarIds) {
        return visibleStarIds.contains(starConnection.getStar1()) || visibleStarIds.contains(starConnection.getStar2());
    }
}
