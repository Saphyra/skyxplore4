package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
    void deleteByExternalReferenceAndGameIdAndPlayerId(String externalReference, String gameId, String playerId);

    void deleteByGameId(String gameId);

    List<ReservationEntity> getByStarIdAndStorageTypeAndGameIdAndPlayerId(String starId, StorageType storageType, String gameId, String playerId);

    List<ReservationEntity> getByStarIdAndDataIdAndGameIdAndPlayerId(String starId, String dataId, String gameId, String playerId);
}
