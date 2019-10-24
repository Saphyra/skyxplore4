package com.github.saphyra.skyxplore.data.gamedata.building.storage;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StorageBuildingService extends AbstractDataService<String, StorageBuilding> {
    public StorageBuildingService(FileUtil fileUtil) {
        super("public/data/gamedata/building/storage", fileUtil);
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(StorageBuilding.class);
        log.info("StorageBuildingService: {}", this);
    }

    @Override
    public void addItem(StorageBuilding content, String fileName) {
        put(content.getId(), content);
    }
}
