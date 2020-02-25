package com.github.saphyra.skyxplore.app.domain.user;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.app.common.event.UserDeletedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.SkyXpUser;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.UserRepository;
import com.github.saphyra.skyxplore.app.domain.user.request.ChangePasswordRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.ChangeUsernameRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.DeleteAccountRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.RegistrationRequest;
import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@Slf4j
@RequiredArgsConstructor
class UserController {
    private static final String CHANGE_PASSWORD_MAPPING = RequestConstants.API_PREFIX + "/user/password";
    private static final String CHANGE_USERNAME_MAPPING = RequestConstants.API_PREFIX + "/user/name";
    private static final String DELETE_ACCOUNT_MAPPING = RequestConstants.API_PREFIX + "/user";

    private final ApplicationEventPublisher applicationEventPublisher;
    private final IdGenerator idGenerator;
    private final RequestContextHolder requestContextHolder;
    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final UuidConverter uuidConverter;

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        SkyXpUser user = getUser();

        if (!passwordService.authenticate(request.getOldPassword(), user.getPassword())) {
            throw ExceptionFactory.invalidPassword();
        }

        user.setPassword(passwordService.hashPassword(request.getNewPassword()));
        userRepository.save(user);
    }

    @PostMapping(CHANGE_USERNAME_MAPPING)
    void changeUsername(@RequestBody @Valid ChangeUsernameRequest request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw ExceptionFactory.usernameAlreadyExists(request.getUserName());
        }

        SkyXpUser user = getUser();
        if (!passwordService.authenticate(request.getPassword(), user.getPassword())) {
            throw ExceptionFactory.invalidPassword();
        }

        user.setUserName(request.getUserName());
        userRepository.save(user);
    }

    @DeleteMapping(DELETE_ACCOUNT_MAPPING)
    void deleteAccount(@RequestBody @Valid DeleteAccountRequest request) {
        SkyXpUser user = getUser();

        if (!passwordService.authenticate(request.getPassword(), user.getPassword())) {
            throw ExceptionFactory.invalidPassword();
        }

        UUID userId = uuidConverter.convertEntity(user.getUserId());
        applicationEventPublisher.publishEvent(new UserDeletedEvent(userId));
        userRepository.delete(user);
    }

    private SkyXpUser getUser() {
        UUID userId = requestContextHolder.get().getUserId();
        return userRepository.findById(uuidConverter.convertDomain(userId)).
            orElseThrow(() -> ExceptionFactory.userNotFound(userId));
    }

    @PutMapping(RequestConstants.REGISTRATION_MAPPING)
    void registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        if (userRepository.findByUserName(registrationRequest.getUserName()).isPresent()) {
            throw ExceptionFactory.usernameAlreadyExists(registrationRequest.getUserName());
        }

        SkyXpUser user = SkyXpUser.builder()
            .userId(idGenerator.generateRandomId())
            .userName(registrationRequest.getUserName())
            .password(passwordService.hashPassword(registrationRequest.getPassword()))
            .build();

        userRepository.save(user);
    }
}
