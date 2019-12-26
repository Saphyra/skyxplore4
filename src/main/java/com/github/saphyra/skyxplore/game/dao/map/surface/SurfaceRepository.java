package com.github.saphyra.skyxplore.game.dao.map.surface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurfaceRepository  extends JpaRepository<SurfaceEntity, String> {
    void deleteByGameId(String gameId);

    List<SurfaceEntity> getByStarIdAndGameIdAndPlayerId(String starId, String gameId, String playerId);

    Optional<SurfaceEntity> findBySurfaceIdAndGameIdAndPlayerId(String surfaceId, String gameId, String playerId);
}
