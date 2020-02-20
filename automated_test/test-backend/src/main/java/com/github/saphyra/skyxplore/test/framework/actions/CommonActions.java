package com.github.saphyra.skyxplore.test.framework.actions;


import com.github.saphyra.skyxplore.test.framework.RequestFactory;
import com.github.saphyra.skyxplore.test.framework.UrlFactory;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class CommonActions {
    public static Response logout(AccessCookies accessCookies) {
        return RequestFactory.createAuthorizedRequest(accessCookies)
            .post(UrlFactory.assemble("/api/logout"))
            .thenReturn();
    }
}
