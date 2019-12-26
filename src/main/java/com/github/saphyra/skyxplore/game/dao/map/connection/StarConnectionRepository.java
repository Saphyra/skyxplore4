package com.github.saphyra.skyxplore.game.dao.map.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface StarConnectionRepository extends JpaRepository<StarConnectionEntity, String> {
    void deleteByGameId(String gameId);

    List<StarConnectionEntity> getByGameIdAndUserId(String gameId, String userId);
}
