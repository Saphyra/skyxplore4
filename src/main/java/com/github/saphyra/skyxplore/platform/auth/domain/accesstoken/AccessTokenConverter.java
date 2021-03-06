package com.github.saphyra.skyxplore.platform.auth.domain.accesstoken;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenConverter extends ConverterBase<SkyXpAccessToken, AccessToken> {
    @Override
    protected AccessToken processEntityConversion(SkyXpAccessToken skyXpAccessToken) {
        return AccessToken.builder()
            .accessTokenId(skyXpAccessToken.getAccessTokenId())
            .userId(skyXpAccessToken.getUserId())
            .lastAccess(skyXpAccessToken.getLastAccess())
            .build();
    }

    @Override
    protected SkyXpAccessToken processDomainConversion(AccessToken accessToken) {
        return SkyXpAccessToken.builder()
            .accessTokenId(accessToken.getAccessTokenId())
            .userId(accessToken.getUserId())
            .lastAccess(accessToken.getLastAccess())
            .build();
    }
}
