package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CitizenDao extends AbstractDao<CitizenEntity, Citizen, String, CitizenRepository> {
    private final UuidConverter uuidConverter;

    public CitizenDao(Converter<CitizenEntity, Citizen> converter, CitizenRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public Integer countByLocation(LocationType locationType, UUID locationId) {
        return repository.countByLocationTypeAndLocationId(locationType, uuidConverter.convertDomain(locationId));
    }

    public List<Citizen> getByLocation(UUID locationID, LocationType locationType) {
        return converter.convertEntity(repository.getByLocationTypeAndLocationId(locationType, uuidConverter.convertDomain(locationID)));
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(userId)
        );
    }

    public void saveAll(List<Citizen> citizens) {
        repository.saveAll(converter.convertDomain(citizens));
    }
}
