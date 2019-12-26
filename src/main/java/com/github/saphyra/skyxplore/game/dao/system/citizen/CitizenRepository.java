package com.github.saphyra.skyxplore.game.dao.system.citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String> {
    Integer countByLocationTypeAndLocationIdAndGameIdAndOwnerId(LocationType locationType, String locationId, String gameId, String ownerId);

    void deleteByGameIdAndUserId(String gameId, String userId);

    List<CitizenEntity> getByLocationTypeAndLocationId(LocationType locationType, String convertDomain);
}
