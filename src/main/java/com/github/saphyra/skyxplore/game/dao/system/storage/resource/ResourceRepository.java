package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
interface ResourceRepository extends CrudRepository<ResourceEntity, String> {
    @Modifying
    @Query("DELETE FROM ResourceEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Modifying
    @Query("DELETE FROM ResourceEntity e WHERE e.gameId = :gameId AND e.round < :round")
    @Transactional
    void deleteByGameIdAndRoundBefore(@Param("gameId") String gameId, @Param("round") int round);

    @Transactional
    void deleteByResourceIdIn(List<String> ids);

    Optional<ResourceEntity> findByStarIdAndDataIdAndRoundAndPlayerId(String starId, String dataId, int round, String playerId);

    Optional<ResourceEntity> findTopByStarIdAndDataIdAndPlayerIdOrderByRoundDesc(String starId, String dataId, String playerId);

    List<ResourceEntity> getByGameId(String gameId);

    List<ResourceEntity> getByGameIdAndRound(String gameId, Integer round);

    List<ResourceEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId);

    List<ResourceEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId);
}
