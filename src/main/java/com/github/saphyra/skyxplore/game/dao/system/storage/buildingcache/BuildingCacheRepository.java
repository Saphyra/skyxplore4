package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BuildingCacheRepository extends CrudRepository<BuildingCacheEntity, String> {
    @Modifying
    @Query("DELETE FROM BuildingCacheEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByBuildingCacheIdIn(List<String> ids);

    List<BuildingCacheEntity> getByGameId(String gameId);
}
