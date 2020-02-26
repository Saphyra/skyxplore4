package com.github.saphyra.skyxplore.test.backend.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.domain.user.request.DeleteAccountRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.SettingsPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class DeleteAccountTest extends TestBase {
    private static final String INCORRECT_PASSWORD = "incorrect-password";

    @Test
    public void invalidPassword() throws IOException {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        DeleteAccountRequest request = DeleteAccountRequest.builder()
            .password(INCORRECT_PASSWORD)
            .build();
        //WHEN
        Response response = SettingsPageActions.deleteAccount(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Hibás jelszó.");
    }

    @Test
    public void successfullyDeletedAccount() throws IOException {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        DeleteAccountRequest request = DeleteAccountRequest.builder()
            .password(registrationRequest.getPassword())
            .build();
        //WHEN
        Response response = SettingsPageActions.deleteAccount(accessCookies, request);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        Response failedAuthorizedResponse = SettingsPageActions.setLocale(accessCookies, "hu");
        assertThat(failedAuthorizedResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse failedAuthorizedErrorResponse = ResponseConverter.convert(failedAuthorizedResponse, ErrorResponse.class);
        assertThat(failedAuthorizedErrorResponse.getErrorCode()).isEqualTo(ErrorCode.SESSION_EXPIRED.name());
        assertThat(failedAuthorizedErrorResponse.getLocalizedMessage()).isEqualTo("A bejelentkezés lejárt.");

        Response failedLoginResponse = IndexPageActions.getLoginResponse(registrationRequest.getUserName(), registrationRequest.getPassword());
        assertThat(failedLoginResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse failedLoginErrorResponse = ResponseConverter.convert(failedLoginResponse, ErrorResponse.class);
        assertThat(failedLoginErrorResponse.getErrorCode()).isEqualTo(ErrorCode.BAD_CREDENTIALS.name());
        assertThat(failedLoginErrorResponse.getLocalizedMessage()).isEqualTo("Hibás felhasználónév vagy jelszó.");
    }
}
