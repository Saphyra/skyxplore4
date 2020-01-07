package com.github.saphyra.skyxplore.game.dao.system.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//TODO create index
interface BuildingRepository extends JpaRepository<BuildingEntity, String> {
    @Modifying
    @Query("DELETE FROM BuildingEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<BuildingEntity> findByBuildingIdAndPlayerId(String buildingId, String playerId);

    Optional<BuildingEntity> findBySurfaceIdAndPlayerId(String surfaceId, String playerId);

    List<BuildingEntity> getByStarIdAndBuildingDataIdAndPlayerId(String starId, String dataId, String playerId);
}
