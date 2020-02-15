package com.github.saphyra.skyxplore_deprecated.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.util.ObjectMapperWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Component
public class CustomObjectMapperWrapper extends ObjectMapperWrapper {
    private final ObjectMapper objectMapper;

    public CustomObjectMapperWrapper(ObjectMapper objectMapper) {
        super(objectMapper);
        this.objectMapper = objectMapper;
    }

    public <T> Collection<? extends T> readValue(InputStream inputStream, TypeReference<List<String>> ref) {
        try {
            return objectMapper.readValue(inputStream, ref);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
