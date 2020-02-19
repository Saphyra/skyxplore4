package com.github.saphyra.skyxplore.test.framework.actions;

import com.github.saphyra.authservice.auth.domain.LoginRequest;
import com.github.saphyra.skyxplore.app.domain.user.request.RegistrationRequest;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexPageActions {
    public static Response registerUser(RegistrationRequest registrationRequest) {
        String uri = UrlFactory.assemble("/api/user");
        Response response = RequestFactory.createRequest()
            .body(registrationRequest)
            .when()
            .put(uri)
            .thenReturn();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        return response;
    }

    public static AccessCookies login(String username, String password) {
        Response response = RequestFactory.createRequest()
            .body(new LoginRequest(username, password, false))
            .when()
            .post(UrlFactory.assemble("/api/login"))
            .thenReturn();
        return AccessCookies.builder()
            .accessTokenId(response.getCookie("accesstokenid"))
            .userId(response.getCookie("userId"))
            .build();
    }

    public static AccessCookies registerAndLogin() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters().toRegistrationRequest();
        registerUser(registrationRequest);
        return login(registrationRequest.getUserName(), registrationRequest.getPassword());
    }
}
