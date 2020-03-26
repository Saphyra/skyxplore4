package com.github.saphyra.skyxplore.app.game_data.domain.research;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.loader.ContentLoaderFactory;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class ResearchDataService extends ValidationAbstractDataService<String, ResearchData> {
    public ResearchDataService(ContentLoaderFactory contentLoaderFactory, ResearchDataValidator researchDataValidator) {
        super("gamedata/research", contentLoaderFactory, researchDataValidator);
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
