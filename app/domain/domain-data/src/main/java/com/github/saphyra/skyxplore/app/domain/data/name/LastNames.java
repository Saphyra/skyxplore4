package com.github.saphyra.skyxplore.app.domain.data.name;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ClassPathList;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LastNames extends ClassPathList<String> {
    private final Random random;

    LastNames(CustomObjectMapperWrapper objectMapper, Random random) throws IOException {
        super(objectMapper, "data/name/last_name.json");
        this.random = random;
        log.info("LastNames loaded: {}", this);
    }

    public String getRandom() {
        return get(random.randInt(0, size() - 1));
    }
}
