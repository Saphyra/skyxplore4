package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.miscellaneous;

import com.github.saphyra.skyxplore.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.common.data.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
