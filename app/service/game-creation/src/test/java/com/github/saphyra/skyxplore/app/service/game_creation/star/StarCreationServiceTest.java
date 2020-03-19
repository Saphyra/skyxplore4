package com.github.saphyra.skyxplore.app.service.game_creation.star;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.common.utils.Mapping;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.data.name.StarNames;
import com.github.saphyra.skyxplore.app.domain.star.Star;

@RunWith(MockitoJUnitRunner.class)
public class StarCreationServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID_1 = UUID.randomUUID();
    private static final UUID PLAYER_ID_2 = UUID.randomUUID();
    private static final String STAR_NAME_1 = "star-name-1";
    private static final String STAR_NAME_2 = "star-name-2";

    @Mock
    private DomainSaverService domainSaverService;

    @Mock
    private StarFactory starFactory;

    @Mock
    private StarNames starNames;

    @InjectMocks
    private StarCreationService underTest;

    @Mock
    private Star star1;

    @Mock
    private Star star2;

    @Test
    public void createStars() {
        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(2, 2);
        List<Mapping<Coordinate, UUID>> mapping = Arrays.asList(
            new Mapping<>(coordinate1, PLAYER_ID_1),
            new Mapping<>(coordinate2, PLAYER_ID_2)
        );

        given(starNames.getRandomStarName(Collections.emptyList())).willReturn(STAR_NAME_1);
        given(starNames.getRandomStarName(Arrays.asList(STAR_NAME_1))).willReturn(STAR_NAME_2);

        given(starFactory.create(GAME_ID, STAR_NAME_1, coordinate1, PLAYER_ID_1)).willReturn(star1);
        given(starFactory.create(GAME_ID, STAR_NAME_2, coordinate2, PLAYER_ID_2)).willReturn(star2);

        List<Star> result = underTest.createStars(GAME_ID, mapping);

        verify(domainSaverService).addAll(Arrays.asList(star1, star2));
        assertThat(result).containsOnly(star1, star2);
    }
}