package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibilities;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TerraformingPossibilitiesService extends AbstractDataService<SurfaceType, TerraformingPossibilities> {
    public TerraformingPossibilitiesService(FileUtil fileUtil) {
        super("public/data/gamedata/terraforming_possibilities", fileUtil);
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
