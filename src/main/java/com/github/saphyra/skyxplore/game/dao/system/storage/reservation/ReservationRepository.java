package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
    void deleteByExternalReferenceAndGameIdAndPlayerId(String externalReference, String gameId, String playerId);

    @Modifying
    @Query("DELETE FROM ReservationEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    List<ReservationEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId);

    List<ReservationEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId);
}
