package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class BuildingDao extends AbstractDao<BuildingEntity, Building, String, BuildingRepository> {
    private final UuidConverter uuidConverter;

    BuildingDao(Converter<BuildingEntity, Building> converter, BuildingRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    Optional<Building> findByBuildingIdAndPlayerId(UUID buildingId, UUID playerId) {
        return converter.convertEntity(repository.findByBuildingIdAndPlayerId(
            uuidConverter.convertDomain(buildingId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Building> findBySurfaceIdAndPlayerId(UUID surfaceId, UUID playerId) {
        return converter.convertEntity(repository.findBySurfaceIdAndPlayerId(
            uuidConverter.convertDomain(surfaceId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Building> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndBuildingDataIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            uuidConverter.convertDomain(playerId)
        ));
    }

    void saveAll(List<Building> buildings) {
        repository.saveAll(converter.convertDomain(buildings));
    }
}
