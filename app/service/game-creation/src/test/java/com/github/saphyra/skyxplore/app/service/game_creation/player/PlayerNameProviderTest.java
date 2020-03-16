package com.github.saphyra.skyxplore.app.service.game_creation.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.auth.domain.Credentials;
import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.skyxplore.app.auth.UserQueryService;

@RunWith(MockitoJUnitRunner.class)
public class PlayerNameProviderTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String PLAYER_NAME = "player-name";

    @Mock
    private AiNameGenerator aiNameGenerator;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private PlayerNameProvider underTest;

    private User user = User.builder()
        .credentials(Credentials.builder()
            .userName(PLAYER_NAME)
            .build()
        )
        .build();

    @Test
    public void ai() {
        given(aiNameGenerator.generateName(Collections.emptyList())).willReturn(PLAYER_NAME);

        String result = underTest.getPlayerName(true, USER_ID, Collections.emptyList());

        assertThat(result).isEqualTo(PLAYER_NAME);
    }

    @Test
    public void player() {
        given(userQueryService.findByUserIdValidated(USER_ID)).willReturn(user);

        String result = underTest.getPlayerName(false, USER_ID, Collections.emptyList());

        assertThat(result).isEqualTo(PLAYER_NAME);
    }
}