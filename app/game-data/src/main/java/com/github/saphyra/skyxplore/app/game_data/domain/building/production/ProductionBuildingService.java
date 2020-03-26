package com.github.saphyra.skyxplore.app.game_data.domain.building.production;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductionBuildingService extends ValidationAbstractDataService<String, ProductionBuilding> {
    public ProductionBuildingService(ContentLoaderFactory contentLoaderFactory, ProductionBuildingValidator productionBuildingValidator) {
        super("gamedata/building/production", contentLoaderFactory, productionBuildingValidator);
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
