package com.github.saphyra.skyxplore.data.base.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;

@Slf4j
class FileLoader<K, V> extends AbstractLoader<V> {
    private static final JsonFileFilter jsonFilter = new JsonFileFilter();

    private final AbstractDataService<K, V> dataService;
    private final File root;

    FileLoader(Class<V> clazz, AbstractDataService<K, V> dataService, FileUtil fileUtil) {
        super(clazz, fileUtil);
        this.dataService = dataService;
        this.root = new File(dataService.getPath());
    }

    @Override
    public void load() {
        try {
            //TODO fix
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = patternResolver.getResources(dataService.getPath().replace("src/main/resources/", "classpath:") + "/*.json");
            ObjectMapper objectMapper = new ObjectMapper();
            for (Resource resource : resources) {
                dataService.addItem(objectMapper.readValue(resource.getURL(), clazz), resource.getFilename());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
        log.info("Loading elements from file.");
        if (!root.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory. Path: " + root.getAbsolutePath());
        }
        File[] files = root.listFiles(jsonFilter);

        if (isNull(files)) {
            log.warn("No files found in directory {}", root);
            return;
        }
        for (File file : files) {
            log.debug("Loading item {}", file.getName());
            loadFile(file);
        }*/
    }

    private void loadFile(File file) {
        dataService.addItem(fileUtil.readValue(file, clazz), file.getName());
    }
}
