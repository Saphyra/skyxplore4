package com.github.saphyra.skyxplore.app.domain.user.accesstoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Repository
//TODO unit test
public interface AccessTokenRepository extends JpaRepository<SkyXpAccessToken, String> {
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE SkyXpAccessToken a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") OffsetDateTime expiration);
}
