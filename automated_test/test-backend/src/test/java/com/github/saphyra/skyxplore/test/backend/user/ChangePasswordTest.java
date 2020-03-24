package com.github.saphyra.skyxplore.test.backend.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.ChangePasswordRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.CommonActions;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.SettingsPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class ChangePasswordTest extends TestBase {
    private static final String NEW_PASSWORD = "new-password";
    private static final String INCORRECT_PASSWORD = "incorrect-password";
    private static final String TOO_SHORT_PASSWORD = "tp";
    private static final String TOO_LONG_PASSWORD = Stream.generate(() -> "a")
        .limit(31)
        .collect(Collectors.joining());

    @Test
    public void tooShortNewPassword() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
            .newPassword(TOO_SHORT_PASSWORD)
            .oldPassword(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changePassword(accessCookies, changePasswordRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void tooLongNewPassword() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
            .newPassword(TOO_LONG_PASSWORD)
            .oldPassword(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changePassword(accessCookies, changePasswordRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void incorrectOldPassword() throws IOException {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
            .newPassword(NEW_PASSWORD)
            .oldPassword(INCORRECT_PASSWORD)
            .build();

        //WHEN
        Response response = SettingsPageActions.changePassword(accessCookies, changePasswordRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Hibás jelszó.");
    }

    @Test
    public void successfullyChangedPassword() throws IOException {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
            .newPassword(NEW_PASSWORD)
            .oldPassword(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.changePassword(accessCookies, changePasswordRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        CommonActions.logout(accessCookies);

        Response failedLoginResponse = IndexPageActions.getLoginResponse(registrationRequest.getUserName(), registrationRequest.getPassword());
        assertThat(failedLoginResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = ResponseConverter.convert(failedLoginResponse, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.BAD_CREDENTIALS.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Hibás felhasználónév vagy jelszó.");

        Response successfulLoginResponse = IndexPageActions.getLoginResponse(registrationRequest.getUserName(), NEW_PASSWORD);
        assertThat(successfulLoginResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
