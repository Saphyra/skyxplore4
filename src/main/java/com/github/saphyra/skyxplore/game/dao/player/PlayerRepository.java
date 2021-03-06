package com.github.saphyra.skyxplore.game.dao.player;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
interface PlayerRepository extends CrudRepository<PlayerEntity, String> {
    @Modifying
    @Query("DELETE FROM PlayerEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByPlayerIdIn(List<String> playerIds);

    List<PlayerEntity> getByGameId(String gameId);

    Optional<PlayerEntity> findByGameIdAndPlayerId(String gameId, String playerId);
}
