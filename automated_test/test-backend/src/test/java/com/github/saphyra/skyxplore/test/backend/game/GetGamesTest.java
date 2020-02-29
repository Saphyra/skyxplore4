package com.github.saphyra.skyxplore.test.backend.game;

import com.github.saphyra.skyxplore.app.domain.user.request.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.MainMenuPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import com.github.saphyra.skyxplore.test.framework.response.GameViewResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

public class GetGamesTest extends TestBase {

    private static final String GAME_NAME_1 = "game-name-1";
    private static final String GAME_NAME_2 = "game-name-2";

    @Test
    public void getGames() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());

        MainMenuPageActions.getCreateGameResponse(accessCookies, GAME_NAME_1);
        MainMenuPageActions.getCreateGameResponse(accessCookies, GAME_NAME_2);

        Response response = MainMenuPageActions.getGamesResponse(accessCookies);

        List<GameViewResponse> games = ResponseConverter.readArrayValue(response, GameViewResponse[].class);

        assertThat(games.stream().anyMatch(gameView -> gameView.getGameName().equals(GAME_NAME_1))).isTrue();
        assertThat(games.stream().anyMatch(gameView -> gameView.getGameName().equals(GAME_NAME_2))).isTrue();
        assertThat(games.stream().noneMatch(gameView -> isNull(gameView.getGameId()))).isTrue();
    }
}
