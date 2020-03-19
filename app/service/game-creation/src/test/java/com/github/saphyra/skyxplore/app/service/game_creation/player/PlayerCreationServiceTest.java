package com.github.saphyra.skyxplore.app.service.game_creation.player;

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
import com.github.saphyra.skyxplore.app.domain.player.Player;

@RunWith(MockitoJUnitRunner.class)
public class PlayerCreationServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID_1 = UUID.randomUUID();
    private static final UUID PLAYER_ID_2 = UUID.randomUUID();
    private static final String PLAYER_NAME = "player-name";

    @Mock
    private DomainSaverService domainSaverService;

    @Mock
    private PlayerFactory playerFactory;

    @InjectMocks
    private PlayerCreationService underTest;

    @Mock
    private Player player;

    @Mock
    private Player ai;

    @Test
    public void create() {
        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(2, 2);
        given(playerFactory.create(GAME_ID, USER_ID, false, Collections.emptyList())).willReturn(player);
        given(player.getPlayerName()).willReturn(PLAYER_NAME);
        given(playerFactory.create(GAME_ID, USER_ID, true, Arrays.asList(PLAYER_NAME))).willReturn(ai);

        given(player.getPlayerId()).willReturn(PLAYER_ID_1);
        given(ai.getPlayerId()).willReturn(PLAYER_ID_2);

        List<Mapping<Coordinate, UUID>> result = underTest.create(GAME_ID, USER_ID, Arrays.asList(coordinate1, coordinate2));

        verify(domainSaverService).add(player);
        verify(domainSaverService).add(ai);

        assertThat(result).contains(new Mapping<>(coordinate1, PLAYER_ID_1));
        assertThat(result).contains(new Mapping<>(coordinate2, PLAYER_ID_2));
    }
}