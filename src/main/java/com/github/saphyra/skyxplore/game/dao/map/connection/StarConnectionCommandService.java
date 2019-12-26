package com.github.saphyra.skyxplore.game.dao.map.connection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StarConnectionCommandService {
    private final StarConnectionDao starConnectionDao;

    public void saveAll(List<StarConnection> connections) {
        starConnectionDao.saveAll(connections);
    }
}
