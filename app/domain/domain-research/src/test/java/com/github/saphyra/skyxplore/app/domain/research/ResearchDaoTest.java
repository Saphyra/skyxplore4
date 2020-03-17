package com.github.saphyra.skyxplore.app.domain.research;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class ResearchDaoTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";
    private static final String STAR_ID_STRING = "star-id";
    private static final String PLAYER_ID_STRING = "player-id";

    @Mock
    private ResearchConverter converter;

    @Mock
    private ResearchRepository researchRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private ResearchDao underTest;

    @Mock
    private Research research;

    @Mock
    private ResearchEntity researchEntity;

    @Test
    public void deleteByGameId() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        underTest.deleteByGameId(GAME_ID);

        verify(researchRepository).deleteByGameId(GAME_ID_STRING);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        given(researchRepository.getByStarIdAndPlayerId(STAR_ID_STRING, PLAYER_ID_STRING)).willReturn(Arrays.asList(researchEntity));
        given(converter.convertEntity(Arrays.asList(researchEntity))).willReturn(Arrays.asList(research));

        List<Research> result = underTest.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID);

        assertThat(result).containsExactly(research);
    }
}