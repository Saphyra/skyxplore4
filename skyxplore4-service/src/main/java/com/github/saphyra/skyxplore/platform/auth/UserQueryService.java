package com.github.saphyra.skyxplore.platform.auth;

import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.platform.auth.domain.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserDao userDao;
    private final UuidConverter uuidConverter;

    public User findByUserIdValidated(UUID userId) {
        return userDao.findById(uuidConverter.convertDomain(userId))
            .orElseThrow(() -> ExceptionFactory.userNotFound(userId));
    }
}
