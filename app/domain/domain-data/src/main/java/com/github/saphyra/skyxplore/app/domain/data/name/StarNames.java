package com.github.saphyra.skyxplore.app.domain.data.name;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ClassPathList;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StarNames extends ClassPathList<String> {
    private final Random random;

    StarNames(CustomObjectMapperWrapper objectMapper, Random random) {
        super(objectMapper, "data/name/star_names.json");
        this.random = random;
        log.info("StarNames loaded: {}", this);
    }

    public String getRandomStarName(List<String> usedStarNames) {
        String result;
        int counter = 0;
        do {
            result = get(random.randInt(0, size() - 1));
            counter++;
        } while (usedStarNames.contains(result) || counter == 1000);
        log.debug("StarName generated: {}", result);
        return result;
    }
}
