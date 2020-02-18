package com.github.saphyra.skyxplore.app.domain.user.domain.accesstoken;

import com.github.saphyra.skyxplore.app.common.test.RepositoryTestConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class AccessTokenRepositoryTest {
    private static final String USER_ID_1 = "user-id-1";
    private static final String USER_ID_2 = "user-id-2";

    @Autowired
    private AccessTokenRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByUserId() {
        //GIVEN
        SkyXpAccessToken accessToken1 = create(USER_ID_1, OffsetDateTime.now());
        SkyXpAccessToken accessToken2 = create(USER_ID_2, OffsetDateTime.now());
        underTest.save(accessToken1);
        underTest.save(accessToken2);
        //WHEN
        underTest.deleteByUserId(USER_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsExactly(accessToken2);
    }

    @Test
    public void deleteExpired() {
        //GIVEN
        SkyXpAccessToken accessToken1 = create(USER_ID_1, OffsetDateTime.now().plusHours(1));
        SkyXpAccessToken accessToken2 = create(USER_ID_2, OffsetDateTime.now().minusSeconds(1));
        underTest.save(accessToken1);
        underTest.save(accessToken2);
        //WHEN
        underTest.deleteExpired(OffsetDateTime.now());
        //THEN
        assertThat(underTest.findAll()).containsExactly(accessToken1);
    }

    private SkyXpAccessToken create(String userId, OffsetDateTime lastAccess) {
        return SkyXpAccessToken.builder()
            .accessTokenId(UUID.randomUUID().toString())
            .userId(userId)
            .lastAccess(lastAccess)
            .build();
    }
}