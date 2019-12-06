package com.github.saphyra.skyxplore.data.gamedata.domain.research;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;


@Component
public class ResearchDataService extends AbstractDataService<String, ResearchData> {
    public ResearchDataService(FileUtil fileUtil) {
        super("public/data/gamedata/research", fileUtil);
    }

    @Override
    @PostConstruct
    public void init() {
        load(ResearchData.class);
    }

    @Override
    public void addItem(ResearchData content, String fileName) {
        put(content.getId(), content);
    }
}
