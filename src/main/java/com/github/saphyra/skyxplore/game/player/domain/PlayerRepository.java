package com.github.saphyra.skyxplore.game.player.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PlayerRepository extends JpaRepository<PlayerEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);
}
