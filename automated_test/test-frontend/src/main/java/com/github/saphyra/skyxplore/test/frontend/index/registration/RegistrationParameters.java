package com.github.saphyra.skyxplore.test.frontend.index.registration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RegistrationParameters {
    private final String userName;
    private final String password;
    private final String confirmPassword;
}
