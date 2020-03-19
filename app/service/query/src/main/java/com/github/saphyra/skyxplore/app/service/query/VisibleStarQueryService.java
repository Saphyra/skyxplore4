package com.github.saphyra.skyxplore.app.service.query;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class VisibleStarQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StarQueryService starQueryService;

    public List<Star> getVisibleStars() {
        UUID playerId = requestContextHolder.get().getPlayerId();
        return starQueryService.getByOwnerId().stream()
            .filter(star -> isVisible(star, playerId))
            .collect(Collectors.toList());

    }

    private boolean isVisible(Star star, UUID playerId) {
        return star.getOwnerId().equals(playerId);
    }
}
