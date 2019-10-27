package com.github.saphyra.skyxplore.game.system.building.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BuildingRepository extends JpaRepository<BuildingEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
