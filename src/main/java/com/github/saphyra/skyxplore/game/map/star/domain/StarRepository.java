package com.github.saphyra.skyxplore.game.map.star.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface StarRepository extends JpaRepository<StarEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);

    List<StarEntity> getByGameIdAndUserId(String gameId, String userId);
}
