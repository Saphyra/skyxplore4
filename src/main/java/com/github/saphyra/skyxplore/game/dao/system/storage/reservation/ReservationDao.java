package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class ReservationDao extends AbstractDao<ReservationEntity, Reservation, String, ReservationRepository> implements DeletableByGameId {
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

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting reservations for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Reservation> getByStarIdAndStorageTypeAndPlayerId(UUID starId, StorageType storageType, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndStorageTypeAndPlayerId(
            uuidConverter.convertDomain(starId),
            storageType,
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Reservation> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndDataIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            uuidConverter.convertDomain(playerId)
        ));
    }
}
