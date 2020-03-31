package com.github.saphyra.skyxplore.app.domain.building;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BuildingCommandService implements CommandService<Building> {
    private final BuildingDao buildingDao;

    @Override
    public void deleteByGameId(UUID gameId) {
        buildingDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Building building) {
        buildingDao.save(building);
    }

    @Override
    public void delete(Building building) {
        buildingDao.delete(building);
    }

    @Override
    public void deleteAll(List<Building> domains) {
        buildingDao.deleteAll(domains);
    }

    @Override
    public void saveAll(List<Building> domains) {
        buildingDao.saveAll(domains);
    }

    @Override
    public Class<Building> getType() {
        return Building.class;
    }
}