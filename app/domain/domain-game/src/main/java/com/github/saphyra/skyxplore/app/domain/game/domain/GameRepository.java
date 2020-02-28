package com.github.saphyra.skyxplore.app.domain.game.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//TODO unit test
interface GameRepository extends JpaRepository<GameEntity, String> {
    Optional<GameEntity> findByGameIdAndUserId(String gameId, String userId);

    List<GameEntity> getByUserId(String userId);
}
