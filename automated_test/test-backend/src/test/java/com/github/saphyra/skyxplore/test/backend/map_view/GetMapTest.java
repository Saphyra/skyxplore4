package com.github.saphyra.skyxplore.test.backend.map_view;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.testng.annotations.Test;

import com.github.saphyra.skyxplore.app.rest.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarConnectionView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.actions.CommonActions;
import com.github.saphyra.skyxplore.test.framework.actions.MapPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;

public class GetMapTest extends TestBase {
    @Test
    public void getMap() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        AccessCookies accessCookies = CommonActions.registerAndCreateGame(registrationRequest);

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
