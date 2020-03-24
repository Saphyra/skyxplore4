package com.github.saphyra.skyxplore.test.backend.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

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

public class DeleteGameTest extends TestBase {
    private static final String GAME_NAME = "game-name";

    @Test
    public void gameNotFound() throws IOException {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        Response response = MainMenuPageActions.deleteGame(accessCookies, UUID.randomUUID());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.GAME_NOT_FOUND.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("A játék nem található.");
    }

    @Test
    public void deleteGame() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        UUID gameId = MainMenuPageActions.createGame(accessCookies, GAME_NAME);

        Response response = MainMenuPageActions.deleteGame(accessCookies, gameId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        assertThat(MainMenuPageActions.getGames(accessCookies)).isEmpty();
    }
}
