package com.github.saphyra.skyxplore.platform.auth.domain.user;

import com.github.saphyra.authservice.auth.domain.Credentials;
import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserConverter extends ConverterBase<SkyXpUser, User> {
    private final UuidConverter uuidConverter;

    @Override
    protected User processEntityConversion(SkyXpUser skyXpUser) {
        return User.builder()
            .userId(uuidConverter.convertDomain(skyXpUser.getUserId()))
            .credentials(new Credentials(skyXpUser.getUserName(), skyXpUser.getPassword()))
            .roles(new HashSet<>())
            .build();
    }

    @Override
    protected SkyXpUser processDomainConversion(User user) {
        return SkyXpUser.builder()
            .userId(uuidConverter.convertEntity(user.getUserId()))
            .userName(user.getCredentials().getUserName())
            .password(user.getCredentials().getPassword())
            .build();
    }
}
