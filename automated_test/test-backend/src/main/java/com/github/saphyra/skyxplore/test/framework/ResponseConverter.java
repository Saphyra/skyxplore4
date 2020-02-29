package com.github.saphyra.skyxplore.test.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.util.ObjectMapperWrapper;
import io.restassured.response.Response;

import java.util.List;

public class ResponseConverter {
    private static final ObjectMapperWrapper OBJECT_MAPPER = new ObjectMapperWrapper(new ObjectMapper());

    public static <T> T convert(Response response, Class<T> clazz) {
        return OBJECT_MAPPER.readValue(response.getBody().asString(), clazz);
    }

    public static <T> List<T> readArrayValue(Response source, Class<T[]> type) {
        return OBJECT_MAPPER.readArrayValue(source.getBody().asString(), type);
    }
}
