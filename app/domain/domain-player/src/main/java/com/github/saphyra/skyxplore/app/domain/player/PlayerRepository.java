package com.github.saphyra.skyxplore.app.domain.player;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface PlayerRepository extends CrudRepository<PlayerEntity, String> {
    @Modifying
    @Query("DELETE FROM PlayerEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByPlayerIdIn(List<String> playerIds);

    Optional<PlayerEntity> findByGameIdAndPlayerId(String gameId, String playerId);

    List<PlayerEntity> getByGameId(String gameId);
}
