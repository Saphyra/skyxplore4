package com.github.saphyra.skyxplore.game.dao.system.construction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ConstructionRepository extends JpaRepository<ConstructionEntity, String> {
    @Modifying
    @Query("DELETE FROM ConstructionEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<ConstructionEntity> findByConstructionIdAndPlayerId(String constructionId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType constructionType, String externalId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndSurfaceIdAndPlayerId(ConstructionType constructionType, String surfaceId, String playerId);

    List<ConstructionEntity> getByStarIdAndPlayerId(String starId, String playerId);

    void deleteByConstructionIdAndPlayerId(String constructionId, String playerId);
}
