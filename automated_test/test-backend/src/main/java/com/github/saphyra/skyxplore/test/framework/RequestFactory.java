package com.github.saphyra.skyxplore.test.framework;

import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.config.DecoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestFactory {
    public static RequestSpecification createAuthorizedRequest(AccessCookies accessCookies){
        return createRequest()
            .cookie("accesstokenid", accessCookies.getAccessTokenId())
            .cookie("userId", accessCookies.getUserId());
    }

    public static RequestSpecification createRequest() {
        return given()
            .config(RestAssuredConfig.config().decoderConfig(DecoderConfig.decoderConfig().contentDecoders(DecoderConfig.ContentDecoder.DEFLATE)))
            .filter(new ResponseLoggingFilter())
            .log().all()
            .contentType(ContentType.JSON)
            .header("Request-type", "rest");
    }
}
