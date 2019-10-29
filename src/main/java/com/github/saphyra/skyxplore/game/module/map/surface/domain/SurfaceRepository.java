package com.github.saphyra.skyxplore.game.module.map.surface.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SurfaceRepository  extends JpaRepository<SurfaceEntity, String> {
    @Query("DELETE SurfaceEntity s WHERE s.gameId = :gameId AND s.userId = :userId")
    @Modifying
    @Transactional
    void deleteByGameIdAndUserId(@Param("gameId") String gameId, @Param("userId") String userId);

    List<SurfaceEntity> getByStarId(String starId);
}
