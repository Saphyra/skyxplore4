package com.github.saphyra.skyxplore.game.dao.map.star;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface StarRepository extends JpaRepository<StarEntity, String> {
    void deleteByGameId(String gameId);

    List<StarEntity> getByGameIdAndOwnerId(String gameId, String ownerId);

    Optional<StarEntity> findByStarIdAndGameIdAndOwnerId(String starId, String gameId, String ownerId);
}
