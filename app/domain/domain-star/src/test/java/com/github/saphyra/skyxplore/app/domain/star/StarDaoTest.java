package com.github.saphyra.skyxplore.app.domain.star;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class StarDaoTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final String STAR_ID_STRING = "star-id";
    private static final String PLAYER_ID_STRING = "player-id";
    private static final UUID PLAYER_ID = UUID.randomUUID();

    @Mock
    private StarConverter starConverter;

    @Mock
    private StarRepository starRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private StarDao underTest;

    @Mock
    private Star star;

    @Mock
    private StarEntity starEntity;

    @Test
    public void deleteByGameId() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        underTest.deleteByGameId(GAME_ID);

        verify(starRepository).deleteByGameId(GAME_ID_STRING);
    }

    @Test
    public void findByStarIdAndOwnerId() {
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);
        given(starRepository.findByStarIdAndOwnerId(STAR_ID_STRING, PLAYER_ID_STRING)).willReturn(Optional.of(starEntity));
        given(starConverter.convertEntity(Optional.of(starEntity))).willReturn(Optional.of(star));

        Optional<Star> result = underTest.findByStarIdAndOwnerId(STAR_ID, PLAYER_ID);

        assertThat(result).contains(star);
    }

    @Test
    public void getByOwnerId() {
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);
        given(starRepository.getByOwnerId(PLAYER_ID_STRING)).willReturn(Arrays.asList(starEntity));
        given(starConverter.convertEntity(Arrays.asList(starEntity))).willReturn(Arrays.asList(star));

        List<Star> result = underTest.getByOwnerId(PLAYER_ID);

        assertThat(result).containsExactly(star);
    }
}