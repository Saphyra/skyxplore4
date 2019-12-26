package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BuildingCommandService {
    private final BuildingDao buildingDao;

    @EventListener
    void gameDeletedEventProcessor(GameDeletedEvent gameDeletedEvent) {
        log.info("Deleting buildings for event {}", gameDeletedEvent);
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
