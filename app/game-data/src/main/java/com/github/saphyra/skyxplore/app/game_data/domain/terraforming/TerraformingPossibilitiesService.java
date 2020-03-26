package com.github.saphyra.skyxplore.app.game_data.domain.terraforming;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.app.common.data.loader.ContentLoaderFactory;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;

@Component
public class TerraformingPossibilitiesService extends ValidationAbstractDataService<SurfaceType, TerraformingPossibilities> {
    public TerraformingPossibilitiesService(ContentLoaderFactory contentLoaderFactory, TerraformingPossibilitiesValidator terraformingPossibilitiesValidator) {
        super("gamedata/terraforming_possibilities", contentLoaderFactory, terraformingPossibilitiesValidator);
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(TerraformingPossibilities.class);
    }

    @Override
    public void addItem(TerraformingPossibilities content, String fileName) {
        put(SurfaceType.parse(FilenameUtils.removeExtension(fileName)), content);
    }
}
