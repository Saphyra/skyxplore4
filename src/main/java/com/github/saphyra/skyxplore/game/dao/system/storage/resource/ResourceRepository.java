package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
    @Modifying
    @Query("DELETE FROM ResourceEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<ResourceEntity> findByStarIdAndDataIdAndRoundAndGameIdAndPlayerId(String starId, String dataId, int round, String gameId, String playerId);

    Optional<ResourceEntity> findLatestByStarIdAndDataIdAndGameIdAndPlayerId(String starId, String dataId, String gameId, String playerId);

    List<ResourceEntity> getByStarIdAndStorageTypeAndGameIdAndPlayerId(String starId, StorageType storageType, String gameId, String playerId);

    List<ResourceEntity> getByStarIdAndDataIdAndGameIdAndPlayerId(String starId, String dataId, String gameId, String playerId);
}
