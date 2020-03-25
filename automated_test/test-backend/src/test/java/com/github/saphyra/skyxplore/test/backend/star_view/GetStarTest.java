package com.github.saphyra.skyxplore.test.backend.star_view;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.github.saphyra.skyxplore.app.rest.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.actions.CommonActions;
import com.github.saphyra.skyxplore.test.framework.actions.MapPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.StarViewActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;

public class GetStarTest extends TestBase {
    @Test
    public void getStar() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        AccessCookies accessCookies = CommonActions.registerAndCreateGame(registrationRequest);

        MapView map = MapPageActions.getMap(accessCookies);
        StarMapView starMapView = map.getStars().get(0);

        StarMapView result = StarViewActions.getStar(accessCookies, starMapView.getStarId());

        assertThat(result).isEqualTo(starMapView);
    }
}
