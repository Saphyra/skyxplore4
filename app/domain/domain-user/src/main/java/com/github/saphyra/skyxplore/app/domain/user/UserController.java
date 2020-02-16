package com.github.saphyra.skyxplore.app.domain.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.app.domain.user.user.SkyXpUser;
import com.github.saphyra.skyxplore.app.domain.user.user.UserRepository;
import com.github.saphyra.skyxplore.common.config.RequestConstants;
import com.github.saphyra.skyxplore.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
//TODO API test
public class UserController {
    //TODO implement
    private static final String CHANGE_PASSWORD_MAPPING = RequestConstants.API_PREFIX + "/user/password";

    //TODO implement
    private static final String CHANGE_USERNAME_MAPPING = RequestConstants.API_PREFIX + "/user/name";

    //TODO implement
    private static final String DELETE_ACCOUNT_MAPPING = RequestConstants.API_PREFIX + "/user";

    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final UuidConverter uuidConverter;

    @PutMapping(RequestConstants.REGISTRATION_MAPPING)
    void registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        if (userRepository.findByUserName(registrationRequest.getUserName()).isPresent()) {
            throw ExceptionFactory.usernameAlreadyExists(registrationRequest.getUserName());
        }

        SkyXpUser user = SkyXpUser.builder()
            .userId(uuidConverter.convertDomain(idGenerator.randomUUID()))
            .userName(registrationRequest.getUserName())
            .password(passwordService.hashPassword(registrationRequest.getPassword()))
            .build();

        userRepository.save(user);
    }
}
