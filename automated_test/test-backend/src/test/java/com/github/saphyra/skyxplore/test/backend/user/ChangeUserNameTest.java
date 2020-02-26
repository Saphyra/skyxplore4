package com.github.saphyra.skyxplore.test.backend.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.domain.user.request.ChangeUsernameRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.CommonActions;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.SettingsPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class ChangeUserNameTest extends TestBase {
    private static final String TOO_LONG_USERNAME = Stream.generate(() -> "a")
        .limit(31)
        .collect(Collectors.joining());
    private static final String INCORRECT_PASSWORD = "incorrect-password";

    @Test
    public void tooShortUsername() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangeUsernameRequest request = ChangeUsernameRequest.builder()
            .userName("aa")
            .password(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changeUsername(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void tooLongUsername() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangeUsernameRequest request = ChangeUsernameRequest.builder()
            .userName(TOO_LONG_USERNAME)
            .password(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changeUsername(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void invalidPassword() throws IOException {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangeUsernameRequest request = ChangeUsernameRequest.builder()
            .userName(RegistrationParameters.validParameters().getUserName())
            .password(INCORRECT_PASSWORD)
            .build();
        //WHEN
        Response response = SettingsPageActions.changeUsername(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Hibás jelszó.");
    }

    @Test
    public void usernameAlreadyExists() throws IOException {
        //GIVEN
        RegistrationRequest existingUserRegistrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(existingUserRegistrationRequest);

        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);
        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangeUsernameRequest request = ChangeUsernameRequest.builder()
            .userName(existingUserRegistrationRequest.getUserName())
            .password(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changeUsername(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.USER_NAME_ALREADY_EXISTS.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Felhasználónév foglalt.");
    }

    @Test
    public void successfullyChangedUsername() throws IOException {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangeUsernameRequest request = ChangeUsernameRequest.builder()
            .userName(RegistrationParameters.validParameters().getUserName())
            .password(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changeUsername(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        CommonActions.logout(accessCookies);

        Response failedLoginResponse = IndexPageActions.getLoginResponse(registrationRequest.getUserName(), registrationRequest.getPassword());
        assertThat(failedLoginResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = ResponseConverter.convert(failedLoginResponse, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.BAD_CREDENTIALS.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Hibás felhasználónév vagy jelszó.");

        Response successfulLoginResponse = IndexPageActions.getLoginResponse(request.getUserName(), registrationRequest.getPassword());
        assertThat(successfulLoginResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
