package com.github.saphyra.skyxplore.app.domain.user.domain.accesstoken;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenConverterTest {
    private static final String ACCESS_TOKEN_ID = "access-token-id";
    private static final String USER_ID = "user-id";
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now();

    @InjectMocks
    private AccessTokenConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        SkyXpAccessToken accessToken = SkyXpAccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(LAST_ACCESS)
            .build();
        //WHEN
        AccessToken result = underTest.convertEntity(accessToken);
        //THEN
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
    }

    @Test
    public void convertDomain() {
        //GIVEN
        AccessToken accessToken = AccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(LAST_ACCESS)
            .build();
        //WHEN
        SkyXpAccessToken result = underTest.convertDomain(accessToken);
        //THEN
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
    }
}