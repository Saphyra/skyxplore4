package com.github.saphyra.skyxplore.game.dao.map.surface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurfaceRepository  extends JpaRepository<SurfaceEntity, String> {
    @Modifying
    @Query("DELETE FROM SurfaceEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    List<SurfaceEntity> getByStarIdAndPlayerId(String starId, String playerId);

    Optional<SurfaceEntity> findBySurfaceIdAndGameIdAndPlayerId(String surfaceId, String gameId, String playerId);
}
