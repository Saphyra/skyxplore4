package com.github.saphyra.skyxplore.game.dao.map.star;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
//TODO cacheRepository
public interface ResearchRepository extends JpaRepository<ResearchEntity, String> {
    @Modifying
    @Query("DELETE FROM ResearchEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<ResearchEntity> getByStarIdAndPlayerId(String starId, String playerId);
}
