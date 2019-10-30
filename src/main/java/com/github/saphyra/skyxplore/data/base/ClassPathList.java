package com.github.saphyra.skyxplore.data.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.saphyra.skyxplore.common.CustomObjectMapperWrapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ClassPathList<T> extends ArrayList<T> {
    public ClassPathList(CustomObjectMapperWrapper objectMapper, String fileLocation){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileLocation);
        TypeReference<List<String>> ref = new TypeReference<List<String>>() {
        };
        addAll(objectMapper.readValue(inputStream, ref));
    }
}
