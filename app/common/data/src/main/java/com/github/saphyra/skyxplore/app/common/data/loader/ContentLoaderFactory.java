package com.github.saphyra.skyxplore.app.common.data.loader;

import com.github.saphyra.skyxplore.app.common.data.AbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.ContentLoader;
import com.github.saphyra.skyxplore.app.common.utils.CustomObjectMapperWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class ContentLoaderFactory {
    private final CustomObjectMapperWrapper customObjectMapperWrapper;
    private final PathMatchingResourcePatternResolver patternResolver;

    public <K, V> ContentLoader getInstance(Class<V> clazz, AbstractDataService<K, V> gameDataService) {
        return new ClassPathLoader<K, V>(
            customObjectMapperWrapper,
            patternResolver,
            gameDataService,
            clazz
        );
    }
}