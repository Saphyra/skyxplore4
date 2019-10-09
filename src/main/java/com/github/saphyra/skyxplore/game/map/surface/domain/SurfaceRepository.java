package com.github.saphyra.skyxplore.game.map.surface.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurfaceRepository  extends JpaRepository<SurfaceEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
