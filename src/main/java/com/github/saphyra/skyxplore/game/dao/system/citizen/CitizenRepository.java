package com.github.saphyra.skyxplore.game.dao.system.citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String> {
    Integer countByLocationTypeAndLocationIdAndGameIdAndOwnerId(LocationType locationType, String locationId, String gameId, String ownerId);

    void deleteByGameId(String gameId);
}
