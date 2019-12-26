package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
class ReservationDao extends AbstractDao<ReservationEntity, Reservation, String, ReservationRepository> {
    private final UuidConverter uuidConverter;

    ReservationDao(ReservationConverter converter, ReservationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByExternalReferenceAndGameIdAndPlayerId(UUID externalReference, UUID gameId, UUID playerId) {
        repository.deleteByExternalReferenceAndGameIdAndPlayerId(
            uuidConverter.convertDomain(externalReference),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        );
    }

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    List<Reservation> getByStarIdAndStorageTypeAndGameIdAndPlayerId(UUID starId, StorageType storageType, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndStorageTypeAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            storageType,
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Reservation> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndDataIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
