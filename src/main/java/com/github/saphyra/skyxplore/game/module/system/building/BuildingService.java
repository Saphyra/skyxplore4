package com.github.saphyra.skyxplore.game.module.system.building;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.module.system.building.domain.BuildingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BuildingService {
    private final BuildingDao buildingDao;

    @EventListener
    void gameDeletedEventProcessor(GameDeletedEvent gameDeletedEvent){
        buildingDao.deleteByGameIdAndUserId(gameDeletedEvent.getGameId(), gameDeletedEvent.getUserId());
    }
}
