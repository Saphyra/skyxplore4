package com.github.saphyra.skyxplore.game.module.system.storage.reservation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
