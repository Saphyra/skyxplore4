package com.github.saphyra.skyxplore.app.domain.user.domain.accesstoken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenDaoTest {
    private static final String USER_ID = "user-id";
    private static final OffsetDateTime EXPIRATION = OffsetDateTime.now();

    @Mock
    private AccessTokenConverter accessTokenConverter;

    @Mock
    private AccessTokenRepository accessTokenRepository;

    @InjectMocks
    private AccessTokenDao underTest;

    @Test
    public void deleteByUserId() {
        //WHEN
        underTest.deleteByUserId(USER_ID);
        //THEN
        verify(accessTokenRepository).deleteByUserId(USER_ID);
    }

    @Test
    public void deleteExpiredAccessTokens() {
        //WHEN
        underTest.deleteExpiredAccessTokens(EXPIRATION);
        //THEN
        verify(accessTokenRepository).deleteExpired(EXPIRATION);
    }
}