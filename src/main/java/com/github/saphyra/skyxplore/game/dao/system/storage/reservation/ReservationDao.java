package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;

@Component
public class ReservationDao extends AbstractDao<ReservationEntity, Reservation, String, ReservationRepository> {
    private final UuidConverter uuidConverter;

    public ReservationDao(ReservationConverter converter, ReservationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    public List<Reservation> getByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return converter.convertEntity(repository.getByStarIdAndStorageType(
            uuidConverter.convertDomain(starId),
            storageType
        ));
    }
}
