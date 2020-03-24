package com.github.saphyra.skyxplore.test.framework.actions;

import com.github.saphyra.skyxplore.app.rest.controller.request.user.ChangePasswordRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.ChangeUsernameRequest;
import com.github.saphyra.skyxplore.app.rest.controller.request.user.DeleteAccountRequest;
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

    public static Response changeUsername(AccessCookies accessCookies, ChangeUsernameRequest request) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .body(request)
            .post(UrlFactory.assemble("/api/user/name"))
            .thenReturn();
    }

    public static Response deleteAccount(AccessCookies accessCookies, DeleteAccountRequest request) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .body(request)
            .delete(UrlFactory.assemble("/api/user"))
            .thenReturn();
    }

    public static Response setLocale(AccessCookies accessCookies, String locale) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .post(String.format(UrlFactory.assemble("/api/user/locale/%s"), locale))
            .thenReturn();
    }

    public static Response setLocaleCookie(String locale) {
        return RequestFactory.createRequest()
            .post(String.format(UrlFactory.assemble("/api/locale/%s"), locale))
            .thenReturn();
    }
}
