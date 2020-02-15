package com.github.saphyra.skyxplore_deprecated.data.gamedata;

import com.github.saphyra.skyxplore_deprecated.common.CustomObjectMapperWrapper;
import com.github.saphyra.skyxplore_deprecated.data.base.ClassPathList;
import com.github.saphyra.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LastNames extends ClassPathList<String> {
    private final Random random;

    LastNames(CustomObjectMapperWrapper objectMapper, Random random) throws IOException {
        super(objectMapper, "public/data/name/last_name.json");
        this.random = random;
        log.info("LastNames loaded: {}", this);
    }

    public String getRandom() {
        return get(random.randInt(0, size() - 1));
    }
}
