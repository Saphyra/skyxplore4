package com.github.saphyra.skyxplore.test.framework.actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import com.github.saphyra.skyxplore.test.framework.response.GameViewResponse;
import io.restassured.response.Response;

public class MainMenuPageActions {
    public static UUID createGame(AccessCookies accessCookies, String gameName) {
        Response response = getCreateGameResponse(accessCookies, gameName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        return UUID.fromString(response.getBody().asString());
    }

    public static Response getCreateGameResponse(AccessCookies accessCookies, String gameName) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .body(new OneStringParamRequest(gameName))
            .put(UrlFactory.assemble("/api/game"))
            .thenReturn();
    }

    public static List<GameViewResponse> getGames(AccessCookies accessCookies) {
        return ResponseConverter.readArrayValue(getGamesResponse(accessCookies), GameViewResponse[].class);
    }

    public static Response getGamesResponse(AccessCookies accessCookies) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .get(UrlFactory.assemble("/api/game"))
            .thenReturn();
    }

    public static Response deleteGame(AccessCookies accessCookies, UUID gameId) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .delete(String.format(UrlFactory.assemble("/api/game/%s"), gameId))
            .thenReturn();
    }

    public static UUID getPlayerId(AccessCookies accessCookies) {
        String cookieValue = RequestFactory.createAuthorizedRequest(accessCookies)
            .get(UrlFactory.assemble("/web/game/" + accessCookies.getGameId()))
            .getCookie("player_id");
        return UUID.fromString(cookieValue);
    }
}
