package com.github.saphyra.skyxplore.test.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;

public class ResponseConverter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T convert(Response response, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(response.getBody().asString(), clazz);
    }
}
