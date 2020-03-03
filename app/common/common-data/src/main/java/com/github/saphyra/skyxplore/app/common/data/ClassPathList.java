package com.github.saphyra.skyxplore.app.common.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;

public class ClassPathList<T> extends ArrayList<T> {
    public ClassPathList(CustomObjectMapperWrapper objectMapper, String fileLocation) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileLocation);
        TypeReference<Collection<T>> ref = new TypeReference<Collection<T>>() {
        };
        addAll(objectMapper.readValue(inputStream, ref));
    }
}
