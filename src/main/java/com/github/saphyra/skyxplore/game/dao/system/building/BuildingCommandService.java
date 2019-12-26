package com.github.saphyra.skyxplore.game.dao.system.building;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BuildingCommandService {
    private final BuildingDao buildingDao;

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
