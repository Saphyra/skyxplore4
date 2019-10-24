package com.github.saphyra.skyxplore.data.gamedata.building.production;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductionBuildingService extends AbstractDataService<String, ProductionBuilding> {
    public ProductionBuildingService(FileUtil fileUtil) {
        super("public/data/gamedata/building/production", fileUtil);
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(ProductionBuilding.class);
        log.info("ProductionBuildingService: {}", this);
    }

    @Override
    public void addItem(ProductionBuilding content, String fileName) {
        put(content.getId(), content);
    }
}
