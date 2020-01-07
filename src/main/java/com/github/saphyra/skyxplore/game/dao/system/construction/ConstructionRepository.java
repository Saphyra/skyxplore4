package com.github.saphyra.skyxplore.game.dao.system.construction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//TODO create index
interface ConstructionRepository extends JpaRepository<ConstructionEntity, String> {
    @Modifying
    @Query("DELETE FROM ConstructionEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<ConstructionEntity> findByConstructionIdAndGameIdAndPlayerId(String constructionId, String gameId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndExternalIdAndGameIdAndPlayerId(ConstructionType constructionType, String externalId, String gameId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(ConstructionType constructionType, String surfaceId, String gameId, String playerId);

    List<ConstructionEntity> getByStarIdAndGameIdAndPlayerId(String starId, String gameId, String playerId);

    void deleteByConstructionIdAndGameIdAndPlayerId(String constructionId, String gameId, String playerId);
}
