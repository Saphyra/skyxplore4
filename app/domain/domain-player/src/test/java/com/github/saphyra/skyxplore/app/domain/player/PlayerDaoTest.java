package com.github.saphyra.skyxplore.app.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class PlayerDaoTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";
    private static final String PLAYER_ID_STRING = "player-id";
    private static final UUID PLAYER_ID = UUID.randomUUID();

    @Mock
    private PlayerConverter playerConverter;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private PlayerDao underTest;

    @Mock
    private Player player;

    @Mock
    private PlayerEntity playerEntity;

    @Before
    public void setUp() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(playerRepository).deleteByGameId(GAME_ID_STRING);
    }

    @Test
    public void getByGameId() {
        given(playerRepository.getByGameId(GAME_ID_STRING)).willReturn(Arrays.asList(playerEntity));
        given(playerConverter.convertEntity(Arrays.asList(playerEntity))).willReturn(Arrays.asList(player));

        List<Player> result = underTest.getByGameId(GAME_ID);

        assertThat(result).containsExactly(player);
    }

    @Test
    public void findPlayerByGameIdAndPlayerId() {
        given(playerRepository.findByGameIdAndPlayerId(GAME_ID_STRING, PLAYER_ID_STRING)).willReturn(Optional.of(playerEntity));
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);
        given(playerConverter.convertEntity(Optional.of(playerEntity))).willReturn(Optional.of(player));

        Optional<Player> result = underTest.findPlayerByGameIdAndPlayerId(GAME_ID, PLAYER_ID);

        assertThat(result).contains(player);
    }
}