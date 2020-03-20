package com.github.saphyra.skyxplore.app.service.query;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnectionQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisibleStarConnectionQueryService {
    private final StarConnectionQueryService starConnectionQueryService;

    public List<StarConnection> getVisibleByStars(List<UUID> visibleStarIds) {
        return starConnectionQueryService.getByGameId().stream()
            .filter(starConnection -> isVisible(starConnection, visibleStarIds))
            .collect(Collectors.toList());
    }

    private boolean isVisible(StarConnection starConnection, List<UUID> visibleStarIds) {
        return visibleStarIds.contains(starConnection.getStar1()) || visibleStarIds.contains(starConnection.getStar2());
    }
}
