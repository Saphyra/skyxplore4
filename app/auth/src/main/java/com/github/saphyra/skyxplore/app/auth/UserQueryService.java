package com.github.saphyra.skyxplore.app.auth;

import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.skyxplore.app.domain.user.user.UserDao;
import com.github.saphyra.skyxplore.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
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
