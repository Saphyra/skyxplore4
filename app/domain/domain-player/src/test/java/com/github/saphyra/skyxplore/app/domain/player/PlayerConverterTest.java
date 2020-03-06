package com.github.saphyra.skyxplore.app.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class PlayerConverterTest {
    private static final String PLAYER_ID_STRING = "player-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final String ENCRYPTED_PLAYER_NAME = "encrypted-player-name";
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_ID_STRING = "user-id";
    private static final String PLAYER_NAME = "player-name";

    @Mock
    private UuidConverter uuidConverter;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private PlayerConverter underTest;

    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getUserId()).willReturn(USER_ID);
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
    }

    @Test
    public void convertEntity() {
        PlayerEntity entity = PlayerEntity.builder()
            .playerId(PLAYER_ID_STRING)
            .gameId(GAME_ID_STRING)
            .ai(true)
            .playerName(ENCRYPTED_PLAYER_NAME)
            .isNew(true)
            .build();

        given(uuidConverter.convertEntity(PLAYER_ID_STRING)).willReturn(PLAYER_ID);
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(stringEncryptor.decryptEntity(ENCRYPTED_PLAYER_NAME, USER_ID_STRING)).willReturn(PLAYER_NAME);

        Player result = underTest.convertEntity(entity);

        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getPlayerName()).isEqualTo(PLAYER_NAME);
        assertThat(result.isAi()).isTrue();
        assertThat(result.isNew()).isTrue();
    }

    @Test
    public void convertDomain() {
        Player player = Player.builder()
            .playerId(PLAYER_ID)
            .gameId(GAME_ID)
            .ai(true)
            .playerName(PLAYER_NAME)
            .isNew(true)
            .build();

        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(stringEncryptor.encryptEntity(PLAYER_NAME, USER_ID_STRING)).willReturn(ENCRYPTED_PLAYER_NAME);

        PlayerEntity result = underTest.convertDomain(player);

        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID_STRING);
        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getPlayerName()).isEqualTo(ENCRYPTED_PLAYER_NAME);
        assertThat(result.isAi()).isTrue();
        assertThat(result.isNew()).isTrue();
    }
}