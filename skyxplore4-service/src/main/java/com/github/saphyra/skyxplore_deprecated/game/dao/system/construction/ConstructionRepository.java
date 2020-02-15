package com.github.saphyra.skyxplore_deprecated.game.dao.system.construction;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
interface ConstructionRepository extends CrudRepository<ConstructionEntity, String> {
    @Transactional
    void deleteByConstructionIdIn(List<String> constructionIds);

    void deleteByConstructionIdAndPlayerId(String constructionId, String playerId);

    @Modifying
    @Query("DELETE FROM ConstructionEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<ConstructionEntity> findByConstructionIdAndPlayerId(String constructionId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType constructionType, String externalId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndSurfaceIdAndPlayerId(ConstructionType constructionType, String surfaceId, String playerId);

    List<ConstructionEntity> getByGameId(String gameId);

    List<ConstructionEntity> getByStarIdAndConstructionTypeAndPlayerId(String starId, ConstructionType constructionType, String playerId);

    List<ConstructionEntity> getByStarIdAndPlayerId(String starId, String playerId);
}
