package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRepository extends CrudRepository<CitizenEntity, String> {
    Integer countByLocationTypeAndLocationIdAndOwnerId(LocationType locationType, String locationId, String ownerId);

    @Transactional
    void deleteByCitizenIdIn(List<String> citizenIds);

    @Modifying
    @Query("DELETE FROM CitizenEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    Optional<CitizenEntity> findByCitizenIdAndOwnerId(String citizenId, String ownerId);

    List<CitizenEntity> getByGameId(String gameId);

    List<CitizenEntity> getByLocationIdAndOwnerId(String locationId, String ownerId);
}
