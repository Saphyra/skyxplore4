package com.github.saphyra.skyxplore.app.common.data.loader;

import com.github.saphyra.skyxplore.app.common.data.AbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.ContentLoader;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Builder
class ClassPathLoader<K, V> implements ContentLoader {
    private final CustomObjectMapperWrapper objectMapperWrapper;
    private final PathMatchingResourcePatternResolver patternResolver;

    private final AbstractDataService<K, V> dataService;
    private final Class<V> clazz;

    @Override
    public void load() {
        try {
            String locationPattern = dataService.getPath().replace("src/main/resources/", "classpath:") + "/*.json";
            log.info("Loading resources for pattern {}", locationPattern);


            Resource[] resources = patternResolver.getResources(locationPattern);
            for (Resource resource : resources) {
                dataService.addItem(objectMapperWrapper.readValue(resource.getURL(), clazz), resource.getFilename());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
