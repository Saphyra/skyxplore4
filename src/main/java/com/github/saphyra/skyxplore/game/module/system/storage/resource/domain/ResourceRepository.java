package com.github.saphyra.skyxplore.game.module.system.storage.resource.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
    void deleteByGameIdAndUserId(String gameId, String userId);

    List<ResourceEntity> getByStarIdAndStorageType(String starId, StorageType storageType);

    Optional<ResourceEntity> findByStarIdAndDataIdAndRound(String starId, String dataId, int round);

    List<ResourceEntity> getByStarIdAndDataId(String starId, String dataId);
}
