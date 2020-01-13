package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StorageSettingRepository extends CrudRepository<StorageSettingEntity, String> {
    @Modifying
    @Query("DELETE FROM StorageSettingEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByStorageSettingIdIn(List<String> ids);

    List<StorageSettingEntity> getByGameId(String gameId);
}
