package com.github.saphyra.skyxplore.test.framework.actions;


import java.util.UUID;

import com.github.saphyra.skyxplore.app.rest.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class CommonActions {
    private static final String GAME_NAME = "game-name";

    public static Response logout(AccessCookies accessCookies) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .post(UrlFactory.assemble("/api/logout"))
            .thenReturn();
    }

    public static AccessCookies registerAndCreateGame(RegistrationRequest registrationRequest) {
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());
        UUID gameId = MainMenuPageActions.createGame(accessCookies, GAME_NAME);
        accessCookies.setGameId(gameId.toString());
        UUID playerId = MainMenuPageActions.getPlayerId(accessCookies);
        accessCookies.setPlayerId(playerId.toString());

        return accessCookies;
    }
}
