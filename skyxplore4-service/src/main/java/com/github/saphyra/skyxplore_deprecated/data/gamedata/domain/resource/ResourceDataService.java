package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource;

import com.github.saphyra.skyxplore.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.common.data.loader.ContentLoaderFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


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
