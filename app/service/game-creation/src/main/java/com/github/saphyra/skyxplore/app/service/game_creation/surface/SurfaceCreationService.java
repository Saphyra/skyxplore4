package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurfaceCreationService {
    private final DomainSaverService domainSaverService;
    private final SurfaceMapFactory surfaceMapFactory;
    private final SurfaceMapper surfaceMapper;

    public Map<Star, List<Surface>> createSurfaces(List<Star> stars) {
        log.info("Creating surfaces...");
        Map<Star, List<Surface>> surfaces = stars.stream()
            .parallel()
            .collect(Collectors.toMap(Function.identity(), this::createSurfaces));
        log.info("Number of surfaces created: {}", surfaces.size());

        domainSaverService.addAll(surfaces.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        return surfaces;
    }

    private List<Surface> createSurfaces(Star star) {
        log.debug("Creating surfaces for star {}", star.getStarId());
        SurfaceType[][] surfaceMap = surfaceMapFactory.createSurfaceMap();
        log.debug("Surfaces created for star {}", star.getStarId());
        return surfaceMapper.mapSurfaces(surfaceMap, star);
    }
}
