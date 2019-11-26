package com.github.saphyra.skyxplore.game.dao.system.construction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ConstructionRepository extends JpaRepository<ConstructionEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);

    Optional<ConstructionEntity> findByConstructionTypeAndExternalId(ConstructionType constructionType, String externalId);

    Optional<ConstructionEntity> findByConstructionTypeAndSurfaceId(ConstructionType constructionType, String surfaceId);

    List<ConstructionEntity> getByStarId(String starId);
}
