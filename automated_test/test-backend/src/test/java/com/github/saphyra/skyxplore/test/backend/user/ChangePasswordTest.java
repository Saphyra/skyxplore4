package com.github.saphyra.skyxplore.test.backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.domain.user.request.ChangePasswordRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.RegistrationRequest;
import com.github.saphyra.skyxplore.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.SettingsPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangePasswordTest extends TestBase {
    private static final String NEW_PASSWORD = "new-password";
    private static final String INCORRECT_PASSWORD = "incorrect-password";

    @Test
    public void tooShortNewPassword(){
        //TODO implement
    }

    @Test
    public void tooLongNewPassword(){
        //TODO implement
    }

    @Test
    public void invalidOldPassword() throws IOException {
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
        ErrorResponse errorResponse = new ObjectMapper().readValue(response.getBody().asString(), ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Hibás jelszó.");
    }

    @Test
    public void successfullyChangedPassword() {
        //TODO implement
    }
}
