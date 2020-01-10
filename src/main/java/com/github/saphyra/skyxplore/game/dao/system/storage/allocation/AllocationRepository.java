package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
interface AllocationRepository extends CrudRepository<AllocationEntity, String> {
    @Transactional
    void deleteByAllocationIdIn(List<String> allocationIds);

    @Transactional
    void deleteByExternalReferenceAndPlayerId(String externalReference, String playerId);

    @Modifying
    @Query("DELETE FROM AllocationEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<AllocationEntity> getByExternalReferenceAndPlayerId(String externalReference, String playerId);

    List<AllocationEntity> getByGameId(String gameId);

    List<AllocationEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId);

    List<AllocationEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId);
}
