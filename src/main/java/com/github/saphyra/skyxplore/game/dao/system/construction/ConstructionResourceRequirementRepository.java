package com.github.saphyra.skyxplore.game.dao.system.construction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ConstructionResourceRequirementRepository extends JpaRepository<ConstructionResourceRequirementEntity, String> {
    @Query("DELETE FROM ConstructionResourceRequirementEntity e WHERE e.gameId = :gameId")
    @Modifying
    void deleteByGameId(@Param("gameId") String gameId);

    List<ConstructionResourceRequirementEntity> getByConstructionId(String constructionId);

    void deleteByConstructionId(String constructionId);
}
