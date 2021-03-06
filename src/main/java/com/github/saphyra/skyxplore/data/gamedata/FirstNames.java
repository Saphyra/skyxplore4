package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.common.CustomObjectMapperWrapper;
import com.github.saphyra.skyxplore.data.base.ClassPathList;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirstNames extends ClassPathList<String> {
    private final Random random;

    FirstNames(CustomObjectMapperWrapper objectMapper, Random random) {
        super(objectMapper, "public/data/name/first_name.json");
        this.random = random;
        log.info("FirstNames loaded: {}", this);
    }

    public String getRandom() {
        return get(random.randInt(0, size() - 1));
    }
}
