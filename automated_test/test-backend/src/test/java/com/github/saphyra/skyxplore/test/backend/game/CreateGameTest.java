package com.github.saphyra.skyxplore.test.backend.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.MainMenuPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class CreateGameTest extends TestBase {
    private static final String TOO_SHORT_GAME_NAME = "ga";
    private static final String TOO_LONG_GAME_NAME = Stream.generate(() -> "s")
        .limit(31)
        .collect(Collectors.joining());
    private static final String VALID_GAME_NAME = "game-name";

    @Test
    public void tooShortGameName() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        Response response = MainMenuPageActions.getCreateGameResponse(accessCookies, TOO_SHORT_GAME_NAME);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_REQUEST.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Érvénytelen paraméter: gameName");
    }

    @Test
    public void tooLongGameName() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        Response response = MainMenuPageActions.getCreateGameResponse(accessCookies, TOO_LONG_GAME_NAME);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_REQUEST.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Érvénytelen paraméter: gameName");
    }

    @Test
    public void createGame() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        Response response = MainMenuPageActions.getCreateGameResponse(accessCookies, VALID_GAME_NAME);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        String responseBody = response.getBody().asString();
        assertThat(UUID.fromString(responseBody)).isNotNull();
    }
}
