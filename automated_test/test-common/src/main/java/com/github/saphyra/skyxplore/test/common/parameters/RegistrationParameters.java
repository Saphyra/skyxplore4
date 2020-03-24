package com.github.saphyra.skyxplore.test.common.parameters;

import com.github.saphyra.skyxplore.app.rest.controller.request.user.RegistrationRequest;
import com.github.saphyra.util.IdGenerator;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder(toBuilder = true)
public class RegistrationParameters {
    private static final IdGenerator ID_GENERATOR = new IdGenerator();

    private static final String VALID_PASSWORD = "valid-password";
    private static final String TOO_SHORT_USERNAME = "to";
    private static final String TOO_SHORT_PASSWORD = "passw";
    private static final String INVALID_CONFIRM_PASSWORD = "invalid-confirm-password";

    private final String userName;
    private final String password;
    private final String confirmPassword;

    public RegistrationRequest toRegistrationRequest() {
        return RegistrationRequest.builder()
            .userName(userName)
            .password(password)
            .build();
    }

    public static RegistrationParameters validParameters() {
        return validParameters(VALID_PASSWORD);
    }

    private static RegistrationParameters validParameters(String password) {
        String[] userNameCharacters = ("user-" + ID_GENERATOR.generateRandomId()).split("");
        String userName = Arrays.stream(userNameCharacters)
            .limit(30)
            .collect(Collectors.joining());
        return RegistrationParameters.builder()
            .userName(userName)
            .password(password)
            .confirmPassword(password)
            .build();
    }

    public static RegistrationParameters tooShortUsernameParameters() {
        return validParameters().toBuilder()
            .userName(TOO_SHORT_USERNAME)
            .build();
    }

    public static RegistrationParameters tooLongUsernameParameters() {
        String userName = Stream.generate(() -> "a")
            .limit(31)
            .collect(Collectors.joining());
        return validParameters().toBuilder()
            .userName(userName)
            .build();
    }

    public static RegistrationParameters tooShortPasswordParameters() {
        return validParameters().toBuilder()
            .password(TOO_SHORT_PASSWORD)
            .confirmPassword(TOO_SHORT_PASSWORD)
            .build();
    }

    public static RegistrationParameters tooLongPasswordParameters() {
        String password = Stream.generate(() -> "a")
            .limit(31)
            .collect(Collectors.joining());
        return validParameters().toBuilder()
            .password(password)
            .confirmPassword(password)
            .build();
    }

    public static RegistrationParameters incorrectConfirmPasswordParameters() {
        return validParameters().toBuilder()
            .confirmPassword(INVALID_CONFIRM_PASSWORD)
            .build();
    }
}
