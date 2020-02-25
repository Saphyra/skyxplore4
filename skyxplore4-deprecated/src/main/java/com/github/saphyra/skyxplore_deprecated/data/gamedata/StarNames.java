package com.github.saphyra.skyxplore_deprecated.data.gamedata;

import com.github.saphyra.skyxplore.app.common.data.ClassPathList;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StarNames extends ClassPathList<String> {
    private final Random random;

    StarNames(CustomObjectMapperWrapper objectMapper, Random random) {
        super(objectMapper, "public/data/name/star_names.json");
        this.random = random;
        log.info("StarNames loaded: {}", this);
    }

    public String getRandomStarName(List<String> usedStarNames) {
        String result;
        do {
            result = get(random.randInt(0, size() - 1));
        } while (usedStarNames.contains(result));
        log.debug("StarName generated: {}", result);
        return result;
    }
}
