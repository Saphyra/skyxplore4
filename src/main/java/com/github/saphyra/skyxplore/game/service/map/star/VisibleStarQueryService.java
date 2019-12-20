package com.github.saphyra.skyxplore.game.service.map.star;

import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.map.star.StarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisibleStarQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StarQueryService starQueryService;

    public List<Star> getVisibleStars() {
        UUID playerId = requestContextHolder.get().getPlayerId();
        return starQueryService.getByGameIdAndOwnerId().stream()
            .filter(star -> isVisible(star, playerId))
            .collect(Collectors.toList());

    }

    private boolean isVisible(Star star, UUID playerId) {
        return star.getOwnerId().equals(playerId);
    }
}
