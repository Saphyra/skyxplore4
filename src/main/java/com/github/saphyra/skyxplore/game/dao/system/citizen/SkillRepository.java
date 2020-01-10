package com.github.saphyra.skyxplore.game.dao.system.citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
//TODO cacheRepository
public interface SkillRepository extends JpaRepository<SkillEntity, String> {
    @Modifying
    @Query("DELETE FROM SkillEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<SkillEntity> getByCitizenId(String citizenId);
}
