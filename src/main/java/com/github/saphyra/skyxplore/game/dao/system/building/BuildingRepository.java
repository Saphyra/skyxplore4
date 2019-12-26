package com.github.saphyra.skyxplore.game.dao.system.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface BuildingRepository extends JpaRepository<BuildingEntity, String> {
    void deleteByGameId(String gameId);

    Optional<BuildingEntity> findByBuildingIdAndPlayerId(String buildingId, String playerId);

    Optional<BuildingEntity> findBySurfaceIdAndPlayerId(String surfaceId, String playerId);

    List<BuildingEntity> getByStarIdAndBuildingDataIdAndPlayerId(String starId, String dataId, String playerId);
}
