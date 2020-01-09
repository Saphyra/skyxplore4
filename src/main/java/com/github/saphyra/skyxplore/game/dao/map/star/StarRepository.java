package com.github.saphyra.skyxplore.game.dao.map.star;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
interface StarRepository extends JpaRepository<StarEntity, String> {
    @Modifying
    @Query("DELETE FROM StarEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    List<StarEntity> getByOwnerId(String ownerId);

    Optional<StarEntity> findByStarIdAndOwnerId(String starId, String ownerId);
}
