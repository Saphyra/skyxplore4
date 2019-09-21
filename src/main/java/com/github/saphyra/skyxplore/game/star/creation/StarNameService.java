package com.github.saphyra.skyxplore.game.star.creation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
class StarNameService extends ArrayList<String> {
    private final Random random;

    StarNameService(ObjectMapper objectMapper, Random random) throws IOException {
        this.random = random;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("public/data/star_names.json");
        TypeReference<List<String>> ref = new TypeReference<List<String>>() {
        };
        addAll(objectMapper.readValue(inputStream, ref));
        log.debug("StarNames loaded: {}", this);
    }

    String getRandomStarName(List<String> usedStarNames) {
        String result;
        do {
            result = get(random.randInt(0, size() - 1));
        } while (usedStarNames.contains(result));
        return result;
    }
}
