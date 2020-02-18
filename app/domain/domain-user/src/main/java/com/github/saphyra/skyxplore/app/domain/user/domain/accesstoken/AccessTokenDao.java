package com.github.saphyra.skyxplore.app.domain.user.domain.accesstoken;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AccessTokenDao extends AbstractDao<SkyXpAccessToken, AccessToken, String, AccessTokenRepository> {
    public AccessTokenDao(AccessTokenConverter converter, AccessTokenRepository repository) {
        super(converter, repository);
    }

    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }

    public void deleteExpiredAccessTokens(OffsetDateTime expiration) {
        repository.deleteExpired(expiration);
    }
}
