package com.github.saphyra.skyxplore.data.gamedata.domain.research;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;


@Component
@Slf4j
public class ResearchDataService extends AbstractDataService<String, ResearchData> {
    public ResearchDataService(FileUtil fileUtil) {
        super("public/data/gamedata/research", fileUtil);
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(ResearchData.class);
        log.info("ResearchDataService: {}", this);
    }

    @Override
    public void addItem(ResearchData content, String fileName) {
        put(content.getId(), content);
    }
}
