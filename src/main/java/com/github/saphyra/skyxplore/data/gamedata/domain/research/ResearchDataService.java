package com.github.saphyra.skyxplore.data.gamedata.domain.research;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Slf4j
public class ResearchDataService extends AbstractDataService<String, ResearchData> {
    public ResearchDataService(ContentLoaderFactory contentLoaderFactory) {
        super("public/data/gamedata/research", contentLoaderFactory);
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
