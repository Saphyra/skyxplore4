package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class CitizenDao extends AbstractDao<CitizenEntity, Citizen, String, CitizenRepository> {
    private final UuidConverter uuidConverter;

    CitizenDao(Converter<CitizenEntity, Citizen> converter, CitizenRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    Integer countByLocationAndAndOwnerId(LocationType locationType, UUID locationId, UUID playerId) {
        return repository.countByLocationTypeAndLocationIdAndOwnerId(
            locationType,
            uuidConverter.convertDomain(locationId),
            uuidConverter.convertDomain(playerId)
        );
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting citizens for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Citizen> getByLocationIdAndOwnerId(UUID locationId, UUID playerId) {
        return converter.convertEntity(repository.getByLocationIdAndOwnerId(
            uuidConverter.convertDomain(locationId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    @Override
    public void saveAll(List<Citizen> citizens) {
        repository.saveAll(converter.convertDomain(citizens));
    }

    public Optional<Citizen> findByCitizenIdAndOwnerId(UUID citizenId, UUID ownerId) {
        return converter.convertEntity(repository.findByCitizenIdAndOwnerId(
            uuidConverter.convertDomain(citizenId),
            uuidConverter.convertDomain(ownerId)
        ));
    }
}
