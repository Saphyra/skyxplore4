package com.github.saphyra.skyxplore.game.module.system.storage.allocation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AllocationRepository extends JpaRepository<AllocationEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
