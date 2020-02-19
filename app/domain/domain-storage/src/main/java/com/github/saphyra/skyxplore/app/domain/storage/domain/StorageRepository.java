package com.github.saphyra.skyxplore.app.domain.storage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, StorageKeyId> {
}
