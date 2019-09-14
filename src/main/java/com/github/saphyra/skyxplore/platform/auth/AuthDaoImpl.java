package com.github.saphyra.skyxplore.platform.auth;

import com.github.saphyra.authservice.auth.AuthDao;
import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.platform.auth.domain.accesstoken.AccessTokenDao;
import com.github.saphyra.skyxplore.platform.auth.domain.user.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthDaoImpl implements AuthDao {
    private final AccessTokenDao accessTokenDao;
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UuidConverter uuidConverter;

    @Override
    public Optional<User> findUserById(String userId) {
        return userDao.findById(uuidConverter.convertEntity(userId));
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public void deleteAccessToken(AccessToken accessToken) {
        accessTokenDao.delete(accessToken);
    }

    @Override
    @Transactional
    public void deleteAccessTokenByUserId(String userId) {
        accessTokenDao.deleteByUserId(uuidConverter.convertEntity(userId));
    }

    @Override
    @Transactional
    public void deleteExpiredAccessTokens(OffsetDateTime expiration) {
        accessTokenDao.deleteExpiredAccessTokens(expiration);
    }

    @Override
    public Optional<AccessToken> findAccessTokenByTokenId(String accessTokenId) {
        return accessTokenDao.findById(uuidConverter.convertEntity(accessTokenId));
    }

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        accessTokenDao.save(accessToken);
    }

    @Override
    public void successfulLogoutCallback(AccessToken deletedAccessToken) {

    }

    @Override
    public void successfulLoginCallback(AccessToken accessToken) {

    }

    @Override
    public boolean authenticate(String enteredPassword, String storedPassword) {
        return passwordService.authenticate(enteredPassword, storedPassword);
    }
}
