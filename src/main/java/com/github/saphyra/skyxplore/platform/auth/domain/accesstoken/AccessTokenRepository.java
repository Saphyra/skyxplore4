package com.github.saphyra.skyxplore.platform.auth.domain.accesstoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface AccessTokenRepository extends JpaRepository<SkyXpAccessToken, UUID> {
    void deleteByUserId(UUID userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE SkyXpAccessToken a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") OffsetDateTime expiration);
}
