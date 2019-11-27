package com.github.saphyra.skyxplore.data.gamedata.domain.building.miscellaneous;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MiscellaneousBuildingService extends AbstractDataService<String, MiscellaneousBuilding> {
    public MiscellaneousBuildingService(FileUtil fileUtil) {
        super("public/data/gamedata/building/miscellaneous", fileUtil);
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
