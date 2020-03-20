package com.github.saphyra.skyxplore.app.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarConnectionView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarConnectionViewConverter;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapViewConverter;
import com.github.saphyra.skyxplore.app.service.query.VisibleStarConnectionQueryService;
import com.github.saphyra.skyxplore.app.service.query.VisibleStarQueryService;

@RunWith(MockitoJUnitRunner.class)
public class MapViewControllerTest {
    private static final UUID STAR_ID = UUID.randomUUID();
    @Mock
    private StarConnectionViewConverter starConnectionViewConverter;

    @Mock
    private StarMapViewConverter starMapViewConverter;

    @Mock
    private VisibleStarConnectionQueryService visibleStarConnectionQueryService;

    @Mock
    private VisibleStarQueryService visibleStarQueryService;

    @InjectMocks
    private MapViewController underTest;

    @Mock
    private Star star;

    @Mock
    private StarMapView starMapView;

    @Mock
    private StarConnection starConnection;

    @Mock
    private StarConnectionView starConnectionView;

    @Test
    public void getMap() {
        given(visibleStarQueryService.getVisibleStars()).willReturn(Arrays.asList(star));
        given(starMapViewConverter.convertDomain(Arrays.asList(star))).willReturn(Arrays.asList(starMapView));

        given(starMapView.getStarId()).willReturn(STAR_ID);

        given(visibleStarConnectionQueryService.getVisibleByStars(Arrays.asList(STAR_ID))).willReturn(Arrays.asList(starConnection));
        given(starConnectionViewConverter.convertDomain(starConnection)).willReturn(starConnectionView);

        MapView result = underTest.getMap();

        assertThat(result.getStars()).containsExactly(starMapView);
        assertThat(result.getConnections()).containsExactly(starConnectionView);
    }
}