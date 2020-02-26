package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.loader.ContentLoaderFactory;


@Component
public class ResourceDataService extends ValidationAbstractDataService<String, ResourceData> {
    public ResourceDataService(ContentLoaderFactory contentLoaderFactory, ResourceValidator resourceValidator) {
        super("public/data/gamedata/resource", contentLoaderFactory, resourceValidator);
    }

    @Override
    @PostConstruct
    public void init() {
        load(ResourceData.class);
    }

    @Override
    public void addItem(ResourceData content, String fileName) {
        put(content.getId(), content);
    }
}
