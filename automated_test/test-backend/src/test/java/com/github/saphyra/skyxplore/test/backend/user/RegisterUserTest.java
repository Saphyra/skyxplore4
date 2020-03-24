package com.github.saphyra.skyxplore.test.backend.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import io.restassured.response.Response;

public class RegisterUserTest extends TestBase {

    @Test
    public void tooShortUsername() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.tooShortUsernameParameters()
            .toRegistrationRequest();
        //WHEN
        Response response = IndexPageActions.getRegistrationResponse(registrationRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void tooLongUsername() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.tooLongUsernameParameters()
            .toRegistrationRequest();
        //WHEN
        Response response = IndexPageActions.getRegistrationResponse(registrationRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void usernameAlreadyExists() throws IOException {
        //GIVEN
        RegistrationRequest existingUserRegistrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(existingUserRegistrationRequest);

        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        registrationRequest.setUserName(existingUserRegistrationRequest.getUserName());
        //WHEN
        Response response = IndexPageActions.getRegistrationResponse(registrationRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.USER_NAME_ALREADY_EXISTS.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Felhasználónév foglalt.");
    }

    @Test
    public void tooShortPassword() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.tooShortPasswordParameters()
            .toRegistrationRequest();
        //WHEN
        Response response = IndexPageActions.getRegistrationResponse(registrationRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void tooLongPassword() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.tooLongPasswordParameters()
            .toRegistrationRequest();
        //WHEN
        Response response = IndexPageActions.getRegistrationResponse(registrationRequest);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void successfulRegistration() {
        //GIVEN
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        //WHEN
        Response response = IndexPageActions.registerUser(registrationRequest);
        //THEN
        IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());
    }
}
