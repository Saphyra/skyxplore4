package com.github.saphyra.skyxplore.game.dao.system.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BuildingRepository extends JpaRepository<BuildingEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);

    List<BuildingEntity> getByStarIdAndBuildingDataId(String starId, String dataId);
}
