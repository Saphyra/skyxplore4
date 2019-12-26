package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.common.interfaces.SaveAllDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class CitizenDao extends AbstractDao<CitizenEntity, Citizen, String, CitizenRepository> implements DeletableByGameId, SaveAllDao<Citizen> {
    private final UuidConverter uuidConverter;

    CitizenDao(Converter<CitizenEntity, Citizen> converter, CitizenRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    Integer countByLocationAndGameIdAndOwnerId(LocationType locationType, UUID locationId, UUID gameId, UUID playerId) {
        return repository.countByLocationTypeAndLocationIdAndGameIdAndOwnerId(
            locationType,
            uuidConverter.convertDomain(locationId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        );
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting citizens for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    @Override
    public void saveAll(List<Citizen> citizens) {
        repository.saveAll(converter.convertDomain(citizens));
    }
}
