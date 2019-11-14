package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AllocationRepository extends JpaRepository<AllocationEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
