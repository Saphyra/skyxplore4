package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.terraforming;

import com.github.saphyra.skyxplore.common.data.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.common.data.loader.ContentLoaderFactory;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TerraformingPossibilitiesService extends ValidationAbstractDataService<SurfaceType, TerraformingPossibilities> {
    public TerraformingPossibilitiesService(ContentLoaderFactory contentLoaderFactory, TerraformingPossibilitiesValidator terraformingPossibilitiesValidator) {
        super("public/data/gamedata/terraforming_possibilities", contentLoaderFactory, terraformingPossibilitiesValidator);
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
