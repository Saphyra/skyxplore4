package com.github.saphyra.skyxplore.game.module.system.citizen.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String> {
    Integer countByLocationTypeAndLocationId(LocationType locationType, String locationId);

    void deleteByGameIdAndUserId(String gameId, String userId);

    List<CitizenEntity> getByLocationTypeAndLocationId(LocationType locationType, String convertDomain);
}
