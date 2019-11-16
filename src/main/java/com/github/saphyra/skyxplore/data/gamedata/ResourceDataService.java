package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import com.github.saphyra.skyxplore.data.gamedata.domain.ResourceData;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ResourceDataService extends AbstractDataService<String, ResourceData> {
    public ResourceDataService(FileUtil fileUtil) {
        super("public/data/gamedata/resource", fileUtil);
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
