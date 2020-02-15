package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SkillRepository extends CrudRepository<SkillEntity, String> {
    @Modifying
    @Query("DELETE FROM SkillEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteBySkillIdIn(List<String> skillIds);

    List<SkillEntity> getByCitizenId(String citizenId);

    List<SkillEntity> getByGameId(String gameId);
}
