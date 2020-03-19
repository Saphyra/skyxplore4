package com.github.saphyra.skyxplore.app.auth;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.UserDao;
import lombok.RequiredArgsConstructor;

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
