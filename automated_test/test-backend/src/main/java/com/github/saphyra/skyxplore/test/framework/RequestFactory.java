package com.github.saphyra.skyxplore.test.framework;

import static io.restassured.RestAssured.given;

import java.util.Optional;

import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.config.DecoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestFactory {
    public static RequestSpecification createAuthorizedRequest(AccessCookies accessCookies) {
        return createRequest()
            .cookie("accesstokenid", emptyIfNull(accessCookies.getAccessTokenId()))
            .cookie("userId", emptyIfNull(accessCookies.getUserId()))
            .cookie("game_id", emptyIfNull(accessCookies.getGameId()))
            .cookie("player_id", emptyIfNull(accessCookies.getPlayerId()));
    }

    public static RequestSpecification createRequest() {
        return given()
            .config(RestAssuredConfig.config().decoderConfig(DecoderConfig.decoderConfig().contentDecoders(DecoderConfig.ContentDecoder.DEFLATE)))
            .filter(new ResponseLoggingFilter())
            .log().all()
            .contentType(ContentType.JSON)
            .header("Request-type", "rest");
    }

    private static String emptyIfNull(String in) {
        return Optional.ofNullable(in)
            .orElse("");
    }
}
