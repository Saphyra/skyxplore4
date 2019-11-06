package com.github.saphyra.skyxplore.game.rest.controller.game;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.game.module.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.rest.view.surface.SurfaceView;
import com.github.saphyra.skyxplore.game.rest.view.surface.SurfaceViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EditSurfaceViewController {
    private static final String GET_SURFACE_DETAILS_MAPPING = API_PREFIX + "/game/surface/{surfaceId}";

    private final SurfaceQueryService surfaceQueryService;
    private final SurfaceViewConverter surfaceViewConverter;

    @GetMapping(GET_SURFACE_DETAILS_MAPPING)
    SurfaceView getSurfaceDetails(@PathVariable("surfaceId") UUID surfaceId) {
        log.info("Querying surface with surfaceId %s", surfaceId);
        return surfaceViewConverter.convertDomain(surfaceQueryService.findBySurfaceId(surfaceId));
    }
}
