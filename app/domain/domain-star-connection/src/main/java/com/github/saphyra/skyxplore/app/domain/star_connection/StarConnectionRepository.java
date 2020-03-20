package com.github.saphyra.skyxplore.app.domain.star_connection;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface StarConnectionRepository extends CrudRepository<StarConnectionEntity, String> {
    @Transactional
    void deleteByConnectionIdIn(List<String> connectionIds);

    @Modifying
    @Query("DELETE FROM StarConnectionEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<StarConnectionEntity> getByGameId(String gameId);
}
