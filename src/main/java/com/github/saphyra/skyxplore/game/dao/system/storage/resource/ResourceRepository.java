package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);

    Optional<ResourceEntity> findByStarIdAndDataIdAndRound(String starId, String dataId, int round);

    Optional<ResourceEntity> findFirstByStarIdAndDataIdOrderByRoundDesc(String starId, String dataId);

    List<ResourceEntity> getByStarIdAndStorageType(String starId, StorageType storageType);

    List<ResourceEntity> getByStarIdAndDataId(String starId, String dataId);
}
