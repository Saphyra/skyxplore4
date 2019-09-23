package com.github.saphyra.skyxplore.game.map.connection;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnectionDao;
import com.github.saphyra.skyxplore.game.map.connection.view.StarConnectionView;
import com.github.saphyra.skyxplore.game.map.connection.view.StarConnectionViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarConnectionQueryService {
    private final StarConnectionDao starConnectionDao;
    private final StarConnectionViewConverter starConnectionViewConverter;

    public List<StarConnectionView> getByGameIdAndUserId(UUID gameId, UUID userId){
        return starConnectionViewConverter.convertDomain(starConnectionDao.getByGameIdAndUserId(gameId, userId));
    }
}
