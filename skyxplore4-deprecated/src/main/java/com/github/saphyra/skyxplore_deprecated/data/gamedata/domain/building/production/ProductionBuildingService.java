package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production;

import com.github.saphyra.skyxplore.app.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ProductionBuildingService extends ValidationAbstractDataService<String, ProductionBuilding> {
    public ProductionBuildingService(ContentLoaderFactory contentLoaderFactory, ProductionBuildingValidator productionBuildingValidator) {
        super("public/data/gamedata/building/production", contentLoaderFactory, productionBuildingValidator);
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
