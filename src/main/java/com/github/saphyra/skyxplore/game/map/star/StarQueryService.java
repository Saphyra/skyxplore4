package com.github.saphyra.skyxplore.game.map.star;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import com.github.saphyra.skyxplore.game.map.star.domain.StarDao;
import com.github.saphyra.skyxplore.game.map.star.view.StarMapView;
import com.github.saphyra.skyxplore.game.map.star.view.StarMapViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarQueryService {
    private final StarDao starDao;
    private final StarMapViewConverter starMapViewConverter;
    private final UuidConverter uuidConverter;

    public List<StarMapView> getVisibleStars(UUID gameId, UUID userId, UUID playerId) {
        List<Star> visibleStars = starDao.getByGameIdAndUserId(gameId, userId).stream()
            .filter(star -> isVisible(star, playerId))
            .collect(Collectors.toList());
        return starMapViewConverter.convertDomain(visibleStars);
    }

    private boolean isVisible(Star star, UUID playerId) {
        return star.getOwnerId().equals(playerId);
    }

    public Star findByStarIdAndUserIdValidated(UUID starId, UUID userId) {
        return starDao.findById(uuidConverter.convertDomain(starId))
            .filter(star -> star.getUserId().equals(userId))
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId));
    }

    StarMapView findDetailsOfStar(UUID playerId, UUID starId) {
        Star star = findByStarIdAndPlayerIdValidated(playerId, starId);
        if(!isVisible(star, playerId)){
            throw ExceptionFactory.invalidStarAccess(playerId, starId);
        }

        return starMapViewConverter.convertDomain(star);
    }

    private Star findByStarIdAndPlayerIdValidated(UUID playerId, UUID starId){
        return starDao.findById(uuidConverter.convertDomain(starId))
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId));
    }
}
