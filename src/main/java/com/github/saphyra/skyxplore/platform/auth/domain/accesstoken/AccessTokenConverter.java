package com.github.saphyra.skyxplore.platform.auth.domain.accesstoken;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenConverter extends ConverterBase<SkyXpAccessToken, AccessToken> {
    private final UuidConverter uuidConverter;

    @Override
    protected AccessToken processEntityConversion(SkyXpAccessToken skyXpAccessToken) {
        return AccessToken.builder()
            .accessTokenId(uuidConverter.convertDomain(skyXpAccessToken.getAccessTokenId()))
            .userId(uuidConverter.convertDomain(skyXpAccessToken.getUserId()))
            .lastAccess(skyXpAccessToken.getLastAccess())
            .build();
    }

    @Override
    protected SkyXpAccessToken processDomainConversion(AccessToken accessToken) {
        return SkyXpAccessToken.builder()
            .accessTokenId(uuidConverter.convertEntity(accessToken.getAccessTokenId()))
            .userId(uuidConverter.convertEntity(accessToken.getUserId()))
            .lastAccess(accessToken.getLastAccess())
            .build();
    }
}
