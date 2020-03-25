package com.github.saphyra.skyxplore.app.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapViewConverter;
import com.github.saphyra.skyxplore.app.service.star.RenameStarService;

@RunWith(MockitoJUnitRunner.class)
public class StarViewControllerTest {
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final String STAR_NAME = "star-name";

    @Mock
    private RenameStarService renameStarService;

    @Mock
    private StarQueryService starQueryService;

    @Mock
    private StarMapViewConverter starMapViewConverter;

    @InjectMocks
    private StarViewController underTest;

    @Mock
    private Star star;

    @Mock
    private StarMapView starMapView;

    @Test
    public void getStar() {
        given(starQueryService.findByStarIdAndOwnerId(STAR_ID)).willReturn(star);
        given(starMapViewConverter.convertDomain(star)).willReturn(starMapView);

        StarMapView result = underTest.getStar(STAR_ID);

        assertThat(result).isEqualTo(starMapView);
    }

    @Test
    public void renameStar() {
        underTest.renameStar(STAR_ID, new OneStringParamRequest(STAR_NAME));

        verify(renameStarService).rename(STAR_ID, STAR_NAME);
    }
}