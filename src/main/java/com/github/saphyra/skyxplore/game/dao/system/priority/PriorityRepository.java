package com.github.saphyra.skyxplore.game.dao.system.priority;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PriorityRepository extends CrudRepository<PriorityEntity, PriorityEntityId> {
    @Modifying
    @Query("DELETE FROM PriorityEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<PriorityEntity> getByGameId(String gameId);
}
