package com.github.saphyra.skyxplore.game.common;

import com.github.saphyra.skyxplore.game.module.SurfaceBuildingSummaryQueryService;
import com.github.saphyra.skyxplore.game.module.system.PopulationQueryService;
import com.github.saphyra.skyxplore.game.module.system.StorageStatusQueryService;
import com.github.saphyra.skyxplore.game.rest.view.system.StarSystemDetailsView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarSystemDetailsQueryService {
    private final PopulationQueryService populationQueryService;
    private final StorageStatusQueryService storageStatusQueryService;
    private final SurfaceBuildingSummaryQueryService surfaceBuildingSummaryQueryService;

    public StarSystemDetailsView getDetailsOfStarSystem(UUID starId){
        return StarSystemDetailsView.builder()
                .storage(storageStatusQueryService.getStorageStatusOfStar(starId))
                .population(populationQueryService.getPopulationOfStar(starId))
                .surfaceBuildings(surfaceBuildingSummaryQueryService.getSummary(starId))
                .build();
    }
}
