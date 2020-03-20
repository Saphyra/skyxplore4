package com.github.saphyra.skyxplore.test.framework.actions;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;

import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class MapPageActions {
    public static MapView getMap(AccessCookies accessCookies) {
        Response response = RequestFactory.createAuthorizedRequest(accessCookies)
            .get(UrlFactory.assemble("/api/game/map"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        return response.getBody()
            .as(MapView.class);
    }
}
