package com.github.saphyra.skyxplore_deprecated.platform.auth;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.platform.auth.domain.RegistrationRequest;
import com.github.saphyra.skyxplore_deprecated.platform.auth.domain.user.SkyXpUser;
import com.github.saphyra.skyxplore_deprecated.platform.auth.domain.user.UserRepository;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private static final String CHANGE_PASSWORD_MAPPING = API_PREFIX + "/user/password";
    private static final String CHANGE_USERNAME_MAPPING = API_PREFIX + "/user/name";
    private static final String DELETE_ACCOUNT_MAPPING = API_PREFIX + "/user";
    public static final String REGISTRATION_MAPPING = API_PREFIX + "/user";

    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final UuidConverter uuidConverter;

    @PutMapping(REGISTRATION_MAPPING)
    void registerUser(@RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.findByUserName(registrationRequest.getUserName()).isPresent()) {
            throw ExceptionFactory.userNameAlreadyExists(registrationRequest.getUserName());
        }

        SkyXpUser user = SkyXpUser.builder()
            .userId(uuidConverter.convertDomain(idGenerator.randomUUID()))
            .userName(registrationRequest.getUserName())
            .password(passwordService.hashPassword(registrationRequest.getPassword()))
            .build();

        userRepository.save(user);
    }
}
