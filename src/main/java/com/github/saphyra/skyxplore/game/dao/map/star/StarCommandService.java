package com.github.saphyra.skyxplore.game.dao.map.star;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class StarCommandService {
    private final StarDao starDao;

    public void saveAll(List<Star> stars) {
        starDao.saveAll(stars);
    }
}
