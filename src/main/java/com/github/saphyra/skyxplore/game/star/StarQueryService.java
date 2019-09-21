package com.github.saphyra.skyxplore.game.star;

import com.github.saphyra.skyxplore.game.star.domain.StarDao;
import com.github.saphyra.skyxplore.game.star.view.StarView;
import com.github.saphyra.skyxplore.game.star.view.StarViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarQueryService {
    private final StarDao starDao;
    private final StarViewConverter starViewConverter;

    public List<StarView> getByGameIdAndUserId(UUID gameId, UUID userId) {
        return starViewConverter.convertDomain(starDao.getByGameIdAndUserId(gameId, userId));
    }
}
