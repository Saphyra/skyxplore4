package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.ContentLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
abstract class AbstractLoader<V> implements ContentLoader {
    protected final Class<V> clazz;
    protected final FileUtil fileUtil;
}