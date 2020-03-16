package com.github.saphyra.skyxplore.app.service.game_creation.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.player.Player;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class PlayerCreationServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final String PLAYER_NAME = "player-name";

    @Mock
    private DomainSaverService domainSaverService;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private PlayerNameProvider playerNameProvider;

    @InjectMocks
    private PlayerCreationService underTest;

    @Test
    public void create() {
        given(idGenerator.randomUUID()).willReturn(PLAYER_ID);
        given(playerNameProvider.getPlayerName(true, USER_ID, Collections.emptyList())).willReturn(PLAYER_NAME);

        Player result = underTest.create(GAME_ID, USER_ID, true, Collections.emptyList());

        verify(domainSaverService).add(result);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getPlayerName()).isEqualTo(PLAYER_NAME);
        assertThat(result.isAi()).isTrue();
        assertThat(result.isNew()).isTrue();
    }
}