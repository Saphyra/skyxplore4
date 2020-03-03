package com.github.saphyra.skyxplore.app.domain.data.name;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ClassPathList;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FirstNames extends ClassPathList<String> {
    private final Random random;

    FirstNames(CustomObjectMapperWrapper objectMapper, Random random) {
        super(objectMapper, "data/name/first_name.json");
        this.random = random;
        log.info("FirstNames loaded: {}", this);
    }

    public String getRandom() {
        return get(random.randInt(0, size() - 1));
    }
}
