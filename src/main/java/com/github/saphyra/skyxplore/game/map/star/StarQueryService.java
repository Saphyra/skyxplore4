package com.github.saphyra.skyxplore.game.map.star;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import com.github.saphyra.skyxplore.game.map.star.domain.StarDao;
import com.github.saphyra.skyxplore.game.map.star.view.StarView;
import com.github.saphyra.skyxplore.game.map.star.view.StarViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarQueryService {
    private final StarDao starDao;
    private final StarViewConverter starViewConverter;
    private final UuidConverter uuidConverter;

    public List<StarView> getByGameIdAndUserId(UUID gameId, UUID userId) {
        return starViewConverter.convertDomain(starDao.getByGameIdAndUserId(gameId, userId));
    }

    public Star findByIdValidated(UUID starId, UUID userId) {
        return starDao.findById(uuidConverter.convertDomain(starId))
            .orElseThrow(() -> ExceptionFactory.starNotFound(starId));
    }
}
