package com.github.saphyra.skyxplore.game.dao.system.construction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ConstructionRepository extends JpaRepository<ConstructionEntity, String> {
    void deleteByGameId(String gameId);

    Optional<ConstructionEntity> findByConstructionIdAndGameIdAndPlayerId(String constructionId, String gameId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndExternalIdAndGameIdAndPlayerId(ConstructionType constructionType, String externalId, String gameId, String playerId);

    Optional<ConstructionEntity> findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(ConstructionType constructionType, String surfaceId, String gameId, String playerId);

    List<ConstructionEntity> getByStarIdAndGameIdAndPlayerId(String starId, String gameId, String playerId);

    void deleteByConstructionIdAndGameIdAndPlayerId(String constructionId, String gameId, String playerId);
}
