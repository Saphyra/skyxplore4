package com.github.saphyra.skyxplore.game.dao.system.construction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ConstructionRepository extends JpaRepository<ConstructionEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
