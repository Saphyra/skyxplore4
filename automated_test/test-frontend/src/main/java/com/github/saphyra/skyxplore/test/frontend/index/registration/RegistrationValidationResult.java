package com.github.saphyra.skyxplore.test.frontend.index.registration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RegistrationValidationResult {
    private final UsernameValidationResult userName;
    private final PasswordValidationResult password;
    private final PasswordValidationResult confirmPassword;

    public boolean allValid() {
        return userName == UsernameValidationResult.VALID
            && password == PasswordValidationResult.VALID
            && confirmPassword == PasswordValidationResult.VALID;
    }
}
