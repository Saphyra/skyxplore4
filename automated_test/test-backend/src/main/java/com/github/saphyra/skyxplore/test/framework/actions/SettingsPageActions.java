package com.github.saphyra.skyxplore.test.framework.actions;

import com.github.saphyra.skyxplore.app.domain.user.request.ChangePasswordRequest;
import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class SettingsPageActions {
    public static Response changePassword(AccessCookies accessCookies, ChangePasswordRequest changePasswordRequest) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .body(changePasswordRequest)
            .post(UrlFactory.assemble("/api/user/password"))
            .thenReturn();
    }
}
