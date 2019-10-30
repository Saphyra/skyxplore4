package com.github.saphyra.skyxplore.game.module.system.citizen.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String> {
    List<CitizenEntity> getByLocationTypeAndLocationId(LocationType locationType, String convertDomain);

    void deleteByGameIdAndUserId(String gameId, String userId);
}
