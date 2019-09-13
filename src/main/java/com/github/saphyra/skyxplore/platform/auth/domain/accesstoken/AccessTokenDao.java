package com.github.saphyra.skyxplore.platform.auth.domain.accesstoken;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class AccessTokenDao extends AbstractDao<SkyXpAccessToken, AccessToken, UUID, AccessTokenRepository> {
    public AccessTokenDao(AccessTokenConverter converter, AccessTokenRepository repository) {
        super(converter, repository);
    }

    public void deleteByUserId(UUID userId) {
        repository.deleteByUserId(userId);
    }

    public void deleteExpiredAccessTokens(OffsetDateTime expiration) {
        repository.deleteExpired(expiration);
    }
}
