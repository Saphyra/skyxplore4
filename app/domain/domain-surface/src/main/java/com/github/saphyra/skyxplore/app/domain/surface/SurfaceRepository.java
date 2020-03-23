package com.github.saphyra.skyxplore.app.domain.surface;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
//TODO unit test
public interface SurfaceRepository extends CrudRepository<SurfaceEntity, String> {
    @Transactional
    @Query("delete from SurfaceEntity e WHERE e.surfaceId in ?1")
    @Modifying
    void deleteBySurfaceIdIn(List<String> surfaceIds);

    @Modifying
    @Query("DELETE FROM SurfaceEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<SurfaceEntity> findBySurfaceIdAndPlayerId(String surfaceId, String playerId);

    List<SurfaceEntity> getByGameId(String gameId);

    List<SurfaceEntity> getByStarIdAndPlayerId(String starId, String playerId);
}
