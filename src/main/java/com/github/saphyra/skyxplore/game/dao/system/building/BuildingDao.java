package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BuildingDao extends AbstractDao<BuildingEntity, Building, String, BuildingRepository> {
    private final UuidConverter uuidConverter;

    public BuildingDao(Converter<BuildingEntity, Building> converter, BuildingRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void saveAll(List<Building> buildings) {
        repository.saveAll(converter.convertDomain(buildings));
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(userId)
        );
    }

    public List<Building> getByStarIdAndDataId(UUID starId, String dataId) {
        return converter.convertEntity(repository.getByStarIdAndBuildingDataId(uuidConverter.convertDomain(starId), dataId));
    }
}
