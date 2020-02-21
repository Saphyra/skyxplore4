package com.github.saphyra.skyxplore.app.domain.storage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, StorageKeyId> {
    @Transactional
    @Modifying
    @Query("DELETE Storage s WHERE s.storageKey.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
