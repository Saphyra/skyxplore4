package com.github.saphyra.skyxplore_deprecated.game.dao.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface GameRepository extends JpaRepository<GameEntity, String> {
    Optional<GameEntity> findByGameIdAndUserId(String gameId, String userId);

    List<GameEntity> getByUserId(String userId);
}
