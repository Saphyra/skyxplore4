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

    Optional<ResourceEntity> findByStarIdAndDataIdAndRoundAndPlayerId(String starId, String dataId, int round, String playerId);

    Optional<ResourceEntity> findLatestByStarIdAndDataIdAndPlayerIdOrderByRoundDesc(String starId, String dataId, String playerId);

    List<ResourceEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId);

    List<ResourceEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId);
}
