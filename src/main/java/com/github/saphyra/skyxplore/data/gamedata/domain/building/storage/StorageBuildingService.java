package com.github.saphyra.skyxplore.data.gamedata.domain.building.storage;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class StorageBuildingService extends AbstractDataService<String, StorageBuilding> {
    public StorageBuildingService(ContentLoaderFactory contentLoaderFactory) {
        super("public/data/gamedata/building/storage", contentLoaderFactory);
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

    public StorageBuilding findByStorageType(StorageType storageType) {
        return values().stream()
                .filter(storageBuilding -> storageBuilding.getStores().equals(storageType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("No storage found for storageType %s.", storageType)));
    }
}
