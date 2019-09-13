package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.ContentLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@RequiredArgsConstructor
@Slf4j
public class ContentLoaderFactory<K, V> {
    private final FileUtil fileUtil;

    public ContentLoader getInstance(Class<V> clazz, AbstractDataService<K, V> gameDataService) {
        File root = new File(gameDataService.getPath());
        if (root.exists()) {
            return new FileLoader<>(clazz, gameDataService, fileUtil);
        } else {
            return new JarLoader<>(clazz, gameDataService, fileUtil);
        }
    }
}

