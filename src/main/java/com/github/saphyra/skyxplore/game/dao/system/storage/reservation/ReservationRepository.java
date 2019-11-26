package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;

@Repository
interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
    void deleteByExternalReference(String externalReference);

    void deleteByGameIdAndUserId(String gameId, String userId);

    List<ReservationEntity> getByStarIdAndStorageType(String starId, StorageType storageType);

    List<ReservationEntity> getByStarIdAndDataId(String starId, String dataId);
}
