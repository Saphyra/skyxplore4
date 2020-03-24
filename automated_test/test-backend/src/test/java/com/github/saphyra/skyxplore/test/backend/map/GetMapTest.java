package com.github.saphyra.skyxplore.test.backend.map;

import com.github.saphyra.skyxplore.app.rest.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarConnectionView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.MainMenuPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.MapPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GetMapTest extends TestBase {
    private static final String GAME_NAME = "game-name";

    @Test
    public void getMap() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        IndexPageActions.registerUser(registrationRequest);

        AccessCookies accessCookies = IndexPageActions.login(registrationRequest.getUserName(), registrationRequest.getPassword());
        UUID gameId = MainMenuPageActions.createGame(accessCookies, GAME_NAME);
        accessCookies.setGameId(gameId.toString());
        UUID playerId = MainMenuPageActions.getPlayerId(accessCookies);
        accessCookies.setPlayerId(playerId.toString());

        MapView map = MapPageActions.getMap(accessCookies);

        assertThat(map.getStars()).hasSize(1);
        StarMapView starMapView = map.getStars().get(0);

        assertThat(map.getConnections()).isNotEmpty();
        map.getConnections().forEach(starConnectionView -> checkConnection(starConnectionView, starMapView));
    }

    private void checkConnection(StarConnectionView starConnectionView, StarMapView starMapView) {
        assertThat(starMapView.getCoordinate().equals(starConnectionView.getCoordinate1()) || starMapView.getCoordinate().equals(starConnectionView.getCoordinate2())).isTrue();
    }
}
