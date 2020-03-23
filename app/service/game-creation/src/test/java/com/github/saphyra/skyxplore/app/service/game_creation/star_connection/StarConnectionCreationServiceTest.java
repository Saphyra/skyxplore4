package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;

@RunWith(MockitoJUnitRunner.class)
public class StarConnectionCreationServiceTest {
    @Mock
    private CloseStarsConnectionCreationService closeStarsConnectionCreationService;

    @Mock
    private ConnectionReducer connectionReducer;

    @Mock
    private DistantStarConnectionCreationService distantStarConnectionCreationService;

    @Mock
    private DomainSaverService domainSaverService;

    @InjectMocks
    private StarConnectionCreationService underTest;

    @Mock
    private Star star;

    @Mock
    private StarConnection connection1;

    @Mock
    private StarConnection getConnection2;

    @Mock
    private StarConnection getConnection3;

    @Test
    public void createConnections() {
        given(closeStarsConnectionCreationService.connectCloseStars(Arrays.asList(star))).willReturn(Arrays.asList(connection1));
        given(distantStarConnectionCreationService.connectDistantStars(Arrays.asList(star), Arrays.asList(connection1))).willReturn(Arrays.asList(connection1, getConnection2, getConnection3));
        given(connectionReducer.removeConnections(Arrays.asList(connection1, getConnection2, getConnection3), Arrays.asList(star))).willReturn(Arrays.asList(getConnection3));

        underTest.createConnections(Arrays.asList(star));

        verify(domainSaverService).addAll(Arrays.asList(getConnection3));
    }
}