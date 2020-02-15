package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class ReservationDao extends AbstractDao<ReservationEntity, Reservation, String, ReservationRepository> {
    private final UuidConverter uuidConverter;

    ReservationDao(ReservationConverter converter, ReservationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByExternalReferenceAndPlayerId(UUID externalReference, UUID playerId) {
        repository.deleteByExternalReferenceAndPlayerId(
            uuidConverter.convertDomain(externalReference),
            uuidConverter.convertDomain(playerId)
        );
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting reservations for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    public List<Reservation> getByGameId(UUID gameId) {
        return converter.convertEntity(repository.getByGameId(
            uuidConverter.convertDomain(gameId)
        ));
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

    Optional<Reservation> findByExternalReferenceAndDataIdAndPlayerId(UUID externalReference, String dataId, UUID playerId) {
        return converter.convertEntity(repository.findByExternalReferenceAndDataIdAndPlayerId(
            uuidConverter.convertDomain(externalReference),
            dataId,
            uuidConverter.convertDomain(playerId)
        ));
    }
}
