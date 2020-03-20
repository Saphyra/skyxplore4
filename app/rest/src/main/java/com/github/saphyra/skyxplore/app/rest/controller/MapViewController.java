package com.github.saphyra.skyxplore.app.rest.controller;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.API_PREFIX;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarConnectionView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarConnectionViewConverter;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapViewConverter;
import com.github.saphyra.skyxplore.app.service.query.VisibleStarConnectionQueryService;
import com.github.saphyra.skyxplore.app.service.query.VisibleStarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
//TODO api test
public class MapViewController {
    private static final String GET_STARS_MAPPING = API_PREFIX + "/game/map";

    private final StarConnectionViewConverter starConnectionViewConverter;
    private final StarMapViewConverter starMapViewConverter;
    private final VisibleStarConnectionQueryService visibleStarConnectionQueryService;
    private final VisibleStarQueryService visibleStarQueryService;

    @GetMapping(GET_STARS_MAPPING)
    MapView getMap() {
        List<StarMapView> visibleStars = starMapViewConverter.convertDomain(visibleStarQueryService.getVisibleStars());
        return MapView.builder()
            .stars(visibleStars)
            .connections(getConnections(visibleStars))
            .build();
    }

    private List<StarConnectionView> getConnections(List<StarMapView> visibleStars) {
        List<UUID> visibleStarIds = visibleStars.stream()
            .map(StarMapView::getStarId)
            .collect(Collectors.toList());
        return visibleStarConnectionQueryService.getVisibleByStars(visibleStarIds).stream()
            .map(starConnectionViewConverter::convertDomain)
            .collect(Collectors.toList());
    }
}
