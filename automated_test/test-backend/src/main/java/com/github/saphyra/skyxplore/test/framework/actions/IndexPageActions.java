package com.github.saphyra.skyxplore.test.framework.actions;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;

import com.github.saphyra.authservice.auth.domain.LoginRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class IndexPageActions {
    public static Response registerUser(RegistrationRequest registrationRequest) {
        Response response = getRegistrationResponse(registrationRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        return response;
    }

    public static Response getRegistrationResponse(RegistrationRequest registrationRequest) {
        String uri = UrlFactory.assemble("/api/user");
        return RequestFactory.createRequest()
            .body(registrationRequest)
            .when()
            .put(uri)
            .thenReturn();
    }

    public static AccessCookies login(String username, String password) {
        Response response = getLoginResponse(username, password);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        return AccessCookies.builder()
            .accessTokenId(response.getCookie("accesstokenid"))
            .userId(response.getCookie("userId"))
            .build();
    }

    public static Response getLoginResponse(String username, String password) {
        return RequestFactory.createRequest()
                .body(new LoginRequest(username, password, false))
                .when()
                .post(UrlFactory.assemble("/api/login"))
                .thenReturn();
    }

    public static AccessCookies registerAndLogin() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters().toRegistrationRequest();
        registerUser(registrationRequest);
        return login(registrationRequest.getUserName(), registrationRequest.getPassword());
    }
}
