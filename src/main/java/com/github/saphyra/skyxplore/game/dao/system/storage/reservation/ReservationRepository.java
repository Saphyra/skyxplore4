package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
interface ReservationRepository extends CrudRepository<ReservationEntity, String> {
    void deleteByExternalReferenceAndPlayerId(String externalReference, String playerId);

    @Modifying
    @Query("DELETE FROM ReservationEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByReservationIdIn(List<String> ids);

    List<ReservationEntity> getByGameId(String gameId);

    List<ReservationEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId);

    List<ReservationEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId);
}
