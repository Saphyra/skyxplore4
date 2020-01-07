package com.github.saphyra.skyxplore.game.dao.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//TODO create index
interface PlayerRepository extends JpaRepository<PlayerEntity, String> {
    @Modifying
    @Query("DELETE FROM PlayerEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);

    List<PlayerEntity> getByGameId(String gameId);

    Optional<PlayerEntity> findByGameIdAndPlayerId(String gameId, String playerId);
}
