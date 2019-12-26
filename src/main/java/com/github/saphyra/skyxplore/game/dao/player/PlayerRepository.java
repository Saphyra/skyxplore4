package com.github.saphyra.skyxplore.game.dao.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PlayerRepository extends JpaRepository<PlayerEntity, String> {
    void deleteByGameId(String gameId);

    List<PlayerEntity> getByUserIdAndGameId(String userId, String gameId);
}
