package com.github.saphyra.skyxplore.test.framework.actions;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class StarViewActions {
    public static Response getRenameStarResponse(AccessCookies accessCookies, UUID starId, String starName) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .body(new OneStringParamRequest(starName))
            .post(UrlFactory.assemble("/api/game/star/" + starId));
    }

    public static StarMapView getStar(AccessCookies accessCookies, UUID starId) {
        Response response = RequestFactory.createAuthorizedRequest(accessCookies)
            .get(UrlFactory.assemble("/api/game/star/" + starId));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        return response.getBody().as(StarMapView.class);
    }
}
