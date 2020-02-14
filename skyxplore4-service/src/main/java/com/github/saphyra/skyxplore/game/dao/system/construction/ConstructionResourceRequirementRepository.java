package com.github.saphyra.skyxplore.game.dao.system.construction;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
interface ConstructionResourceRequirementRepository extends CrudRepository<ConstructionResourceRequirementEntity, String> {
    @Transactional
    void deleteByConstructionId(String constructionId);

    @Transactional
    void deleteByConstructionResourceRequirementIdIn(List<String> ids);

    @Query("DELETE FROM ConstructionResourceRequirementEntity e WHERE e.gameId = :gameId")
    @Modifying
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<ConstructionResourceRequirementEntity> getByConstructionId(String constructionId);

    List<ConstructionResourceRequirementEntity> getByGameId(String gameId);
}
