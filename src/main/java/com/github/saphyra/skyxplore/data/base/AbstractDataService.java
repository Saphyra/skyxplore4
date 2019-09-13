package com.github.saphyra.skyxplore.data.base;

import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import com.github.saphyra.util.OptionalMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public abstract class AbstractDataService<K, V> extends HashMap<K, V> implements OptionalMap<K, V> {
    private static final String BASE_DIR = "src/main/resources/";

    private final ContentLoaderFactory<K, V> contentLoaderFactory;

    @Getter
    private final String path;

    @Getter
    private final String jarPath;

    public AbstractDataService(String path, FileUtil fileUtil) {
        this.path = BASE_DIR + path;
        jarPath = BASE_DIR;
        this.contentLoaderFactory = new ContentLoaderFactory<>(fileUtil);
    }

    protected void load(Class<V> clazz) {
        contentLoaderFactory.getInstance(clazz, this).load();
    }

    public abstract void init();

    public abstract void addItem(V content, String fileName);
}
