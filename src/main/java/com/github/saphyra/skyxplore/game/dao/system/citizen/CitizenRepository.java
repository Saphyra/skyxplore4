package com.github.saphyra.skyxplore.game.dao.system.citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
//TODO cacheRepository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String> {
    Integer countByLocationTypeAndLocationIdAndOwnerId(LocationType locationType, String locationId, String ownerId);

    @Modifying
    @Query("DELETE FROM CitizenEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<CitizenEntity> getByLocationIdAndOwnerId(String locationId, String ownerId);
}
