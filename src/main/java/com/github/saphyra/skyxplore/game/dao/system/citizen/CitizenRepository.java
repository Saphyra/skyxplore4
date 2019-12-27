package com.github.saphyra.skyxplore.game.dao.system.citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String> {
    Integer countByLocationTypeAndLocationIdAndGameIdAndOwnerId(LocationType locationType, String locationId, String gameId, String ownerId);

    @Modifying
    @Query("DELETE FROM CitizenEntity e WHERE e.gameId = :gameId")
    void deleteByGameId(@Param("gameId") String gameId);
}
