package com.github.saphyra.skyxplore_deprecated.game.dao.map.star;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ResearchRepository extends CrudRepository<ResearchEntity, String> {
    @Modifying
    @Query("DELETE FROM ResearchEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByResearchIdIn(List<String> researchIds);

    List<ResearchEntity> getByGameId(String gameId);

    List<ResearchEntity> getByStarIdAndPlayerId(String starId, String playerId);
}
