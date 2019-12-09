package com.github.saphyra.skyxplore.data.gamedata.domain.building.production;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ProductionBuildingService extends AbstractDataService<String, ProductionBuilding> {
    public ProductionBuildingService(ContentLoaderFactory contentLoaderFactory) {
        super("public/data/gamedata/building/production", contentLoaderFactory);
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
