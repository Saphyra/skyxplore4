package com.github.saphyra.skyxplore.game.dao.system.citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, String> {
    @Modifying
    @Query("DELETE FROM SkillEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    List<SkillEntity> getByCitizenId(String citizenId);
}
