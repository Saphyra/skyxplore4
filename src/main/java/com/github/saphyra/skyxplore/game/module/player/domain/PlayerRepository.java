package com.github.saphyra.skyxplore.game.module.player.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PlayerRepository extends JpaRepository<PlayerEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);

    List<PlayerEntity> getByUserIdAndGameId(String userId, String gameId);
}
