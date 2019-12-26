package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.common.interfaces.SaveAllDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class BuildingDao extends AbstractDao<BuildingEntity, Building, String, BuildingRepository> implements DeletableByGameId, SaveAllDao<Building> {
    private final UuidConverter uuidConverter;

    BuildingDao(Converter<BuildingEntity, Building> converter, BuildingRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting buildings for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
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

    @Override
    public void saveAll(List<Building> buildings) {
        repository.saveAll(converter.convertDomain(buildings));
    }
}
