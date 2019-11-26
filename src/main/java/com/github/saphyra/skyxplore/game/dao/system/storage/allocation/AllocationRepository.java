package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;

@Repository
interface AllocationRepository extends JpaRepository<AllocationEntity, String> {
    void deleteByExternalReference(String externalReference);

    void deleteByGameIdAndUserId(String gameId, String userId);

    List<AllocationEntity> getByExternalReference(String externalReference);

    List<AllocationEntity> getByStarIdAndStorageType(String starId, StorageType storageType);

    List<AllocationEntity> getByStarIdAndDataId(String starId, String dataId);
}
