package com.github.saphyra.skyxplore.app.domain.building;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface BuildingRepository extends CrudRepository<BuildingEntity, String> {
    @Transactional
    @Modifying
    @Query("delete from BuildingEntity e WHERE e.buildingId in ?1")
    void deleteByBuildingIdIn(List<String> buildingIds);

    @Modifying
    @Query("DELETE FROM BuildingEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<BuildingEntity> findByBuildingIdAndPlayerId(String buildingId, String playerId);

    Optional<BuildingEntity> findBySurfaceIdAndPlayerId(String surfaceId, String playerId);

    List<BuildingEntity> getByGameId(String gameId);

    List<BuildingEntity> getByStarIdAndBuildingDataIdAndPlayerId(String starId, String dataId, String playerId);

    List<BuildingEntity> getByStarIdAndPlayerId(String starId, String playerId);
}
