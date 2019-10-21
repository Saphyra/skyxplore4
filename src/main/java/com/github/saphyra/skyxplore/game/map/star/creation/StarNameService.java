package com.github.saphyra.skyxplore.game.map.star.creation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class StarNameService extends ArrayList<String> {
    private final Random random;

    StarNameService(ObjectMapper objectMapper, Random random) throws IOException {
        this.random = random;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("public/data/name/star_names.json");
        TypeReference<List<String>> ref = new TypeReference<List<String>>() {
        };
        addAll(objectMapper.readValue(inputStream, ref));
        log.info("StarNames loaded: {}", this);
    }

    String getRandomStarName(List<String> usedStarNames) {
        String result;
        do {
            result = get(random.randInt(0, size() - 1));
        } while (usedStarNames.contains(result));
        log.debug("StarName generated: {}", result);
        return result;
    }
}
