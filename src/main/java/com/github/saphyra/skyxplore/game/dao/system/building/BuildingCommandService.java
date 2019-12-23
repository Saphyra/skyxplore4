package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BuildingCommandService {
    private final BuildingDao buildingDao;

    @EventListener
    void gameDeletedEventProcessor(GameDeletedEvent gameDeletedEvent) {
        buildingDao.deleteByGameIdAndUserId(gameDeletedEvent.getGameId(), gameDeletedEvent.getUserId());
    }

    public void save(Building building) {
        buildingDao.save(building);
    }

    public void delete(Building building) {
        buildingDao.delete(building);
    }

    public void saveAll(List<Building> buildings) {
        buildingDao.saveAll(buildings);
    }
}
