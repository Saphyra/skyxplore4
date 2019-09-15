package com.github.saphyra.skyxplore.game.game.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Game> getByUserId(UUID userId);

    Optional<Game> findByGameIdAndUserId(UUID gameId, UUID userId);
}
