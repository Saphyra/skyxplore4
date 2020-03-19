package com.github.saphyra.skyxplore.app.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.app.domain.user.domain.accesstoken.AccessTokenDao;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class AuthDaoImplTest {
    private static final String USER_ID = "user-id";
    private static final String USER_NAME = "user-name";
    private static final OffsetDateTime EXPIRATION = OffsetDateTime.now();
    private static final String ACCESS_TOKEN_ID = "access-token-id";
    private static final String STORED_PASSWORD = "stored-password";
    private static final String ENTERED_PASSWORD = "entered-password";

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private AuthDaoImpl underTest;

    @Mock
    private User user;

    @Mock
    private AccessToken accessToken;

    @Test
    public void findByUserId() {
        //GIVEN
        given(userDao.findById(USER_ID)).willReturn(Optional.of(user));
        //WHEN
        Optional<User> result = underTest.findUserById(USER_ID);
        //THEN
        assertThat(result).contains(user);
    }

    @Test
    public void findByUserName() {
        //GIVEN
        given(userDao.findByUserName(USER_NAME)).willReturn(Optional.of(user));
        //WHEN
        Optional<User> result = underTest.findUserByUserName(USER_NAME);
        //THEN
        assertThat(result).contains(user);
    }

    @Test
    public void deleteAccessToken() {
        //WHEN
        underTest.deleteAccessToken(accessToken);
        //THEN
        verify(accessTokenDao).delete(accessToken);
    }

    @Test
    public void deleteAccessTokenByUserId() {
        //WHEN
        underTest.deleteAccessTokenByUserId(USER_ID);
        //THEN
        verify(accessTokenDao).deleteByUserId(USER_ID);
    }

    @Test
    public void deleteExpiredAccessTokens() {
        //WHEN
        underTest.deleteExpiredAccessTokens(EXPIRATION);
        //THEN
        verify(accessTokenDao).deleteExpiredAccessTokens(EXPIRATION);
    }

    @Test
    public void findAccessTokenByTokenId() {
        //GIVEN
        given(accessTokenDao.findById(ACCESS_TOKEN_ID)).willReturn(Optional.of(accessToken));
        //WHEN
        Optional<AccessToken> result = underTest.findAccessTokenByTokenId(ACCESS_TOKEN_ID);
        //THEN
        assertThat(result).contains(accessToken);
    }

    @Test
    public void saveAccessToken() {
        //WHEN
        underTest.saveAccessToken(accessToken);
        //THEN
        verify(accessTokenDao).save(accessToken);
    }

    @Test
    public void authenticate() {
        //GIVEN
        given(passwordService.authenticate(ENTERED_PASSWORD, STORED_PASSWORD)).willReturn(true);
        //WHEN
        boolean result = underTest.authenticate(ENTERED_PASSWORD, STORED_PASSWORD);
        //THEN
        assertThat(result).isTrue();
    }
}