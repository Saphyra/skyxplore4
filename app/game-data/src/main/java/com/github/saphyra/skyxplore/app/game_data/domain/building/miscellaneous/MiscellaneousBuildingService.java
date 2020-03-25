package com.github.saphyra.skyxplore.app.game_data.domain.building.miscellaneous;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MiscellaneousBuildingService extends ValidationAbstractDataService<String, MiscellaneousBuilding> {
    public MiscellaneousBuildingService(ContentLoaderFactory contentLoaderFactory, MiscellaneousBuildingValidator buildingValidator) {
        super("public/data/gamedata/building/miscellaneous", contentLoaderFactory, buildingValidator);
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(MiscellaneousBuilding.class);
        log.info("MiscellaneousBuildingService: {}", this);
    }

    @Override
    public void addItem(MiscellaneousBuilding content, String fileName) {
        put(content.getId(), content);
    }
}
