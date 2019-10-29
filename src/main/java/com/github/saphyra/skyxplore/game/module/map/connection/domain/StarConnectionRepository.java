package com.github.saphyra.skyxplore.game.module.map.connection.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StarConnectionRepository extends JpaRepository<StarConnectionEntity, String> {
    List<StarConnectionEntity> getByGameIdAndUserId(String convertDomain, String convertDomain1);

    void deleteByGameIdAndUserId(String gameId, String userId);
}
