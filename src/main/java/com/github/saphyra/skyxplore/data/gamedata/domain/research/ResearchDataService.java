package com.github.saphyra.skyxplore.data.gamedata.domain.research;

import com.github.saphyra.skyxplore.data.base.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Slf4j
public class ResearchDataService extends ValidationAbstractDataService<String, ResearchData> {
    public ResearchDataService(ContentLoaderFactory contentLoaderFactory, ResearchDataValidator researchDataValidator) {
        super("public/data/gamedata/research", contentLoaderFactory, researchDataValidator);
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
