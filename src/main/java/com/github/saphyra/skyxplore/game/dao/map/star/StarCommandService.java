package com.github.saphyra.skyxplore.game.dao.map.star;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class StarCommandService {
    private final StarDao starDao;

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        starDao.deleteByGameIdAndUserId(gameId, userId);
    }

    public void saveAll(List<Star> stars) {
        starDao.saveAll(stars);
    }
}
