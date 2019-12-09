package com.github.saphyra.skyxplore.data.gamedata.domain.resource;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ResourceDataService extends AbstractDataService<String, ResourceData> {
    public ResourceDataService(ContentLoaderFactory contentLoaderFactory) {
        super("public/data/gamedata/resource", contentLoaderFactory);
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
