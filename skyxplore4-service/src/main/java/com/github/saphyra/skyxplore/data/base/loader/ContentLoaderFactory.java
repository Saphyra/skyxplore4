package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.ContentLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@RequiredArgsConstructor
@Slf4j
@Component
public class ContentLoaderFactory {
    private final FileUtil fileUtil;

    public <K, V> ContentLoader getInstance(Class<V> clazz, AbstractDataService<K, V> gameDataService) {
        File root = new File(gameDataService.getPath());
        log.info("Creating ContentLoader for file {}", root);
        if (root.exists() || true) {//TODO fix
            return new FileLoader<>(clazz, gameDataService, fileUtil);
        } else {
            return new JarLoader<>(clazz, gameDataService, fileUtil);
        }
    }
}

