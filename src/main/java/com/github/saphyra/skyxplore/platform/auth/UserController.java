package com.github.saphyra.skyxplore.platform.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private static final String CHANGE_PASSWORD_MAPPING = API_PREFIX + "/user/password";
    private static final String CHANGE_USERNAME_MAPPING = API_PREFIX + "/user/name";
    private static final String DELETE_ACCOUNT_MAPPING = API_PREFIX + "/user";
    public static final String REGISTRATION_MAPPING = API_PREFIX + "/user";
    public static final String USERNAME_EXISTS_MAPPING = API_PREFIX + "/user/name";
}
