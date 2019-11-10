package com.github.saphyra.skyxplore.game.rest.controller.game;

import com.github.saphyra.skyxplore.game.module.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.module.map.surface.TerraformingPossibilitiesQueryService;
import com.github.saphyra.skyxplore.game.rest.view.surface.SurfaceView;
import com.github.saphyra.skyxplore.game.rest.view.surface.SurfaceViewConverter;
import com.github.saphyra.skyxplore.game.rest.view.surface.TerraformingPossibilityView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EditSurfaceViewController {
    private static final String GET_SURFACE_DETAILS_MAPPING = API_PREFIX + "/game/surface/{surfaceId}";
    private static final String GET_TERRAFORMING_POSSIBILITIES = API_PREFIX + "/game/surface/{surfaceId}/terraform";

    private final SurfaceQueryService surfaceQueryService;
    private final SurfaceViewConverter surfaceViewConverter;
    private final TerraformingPossibilitiesQueryService terraformingPossibilitiesQueryService;

    @GetMapping(GET_SURFACE_DETAILS_MAPPING)
    SurfaceView getSurfaceDetails(@PathVariable("surfaceId") UUID surfaceId) {
        log.info("Querying surface with surfaceId {}", surfaceId);
        return surfaceViewConverter.convertDomain(surfaceQueryService.findBySurfaceId(surfaceId));
    }

    @GetMapping(GET_TERRAFORMING_POSSIBILITIES)
    List<TerraformingPossibilityView> getTerraformingPossibilities(@PathVariable("surfaceId") UUID surfaceId){
        log.info("Querying terraforming possibilities of surface {}", surfaceId);
        return terraformingPossibilitiesQueryService.getTerraformingPossibilities(surfaceId);
    }
}
