package com.github.saphyra.skyxplore.game.system.building.domain;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingDao extends AbstractDao<BuildingEntity, Building, String, BuildingRepository> {
    public BuildingDao(Converter<BuildingEntity, Building> converter, BuildingRepository repository) {
        super(converter, repository);
    }

    public void saveAll(List<Building> buildings) {
        repository.saveAll(converter.convertDomain(buildings));
    }
}
