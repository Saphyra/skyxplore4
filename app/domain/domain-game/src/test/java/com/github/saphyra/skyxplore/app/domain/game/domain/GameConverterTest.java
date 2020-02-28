package com.github.saphyra.skyxplore.app.domain.game.domain;

import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameConverterTest {
    private static final String GAME_ID_STRING = "game-id";
    private static final String USER_ID_STRING = "user-id";
    private static final String ENCRYPTED_GAME_NAME = "encrypted-game-name";
    private static final Integer ROUND = 2342;
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String CONTEXT_USER_ID_STRING = "context-user-id";
    private static final String GAME_NAME = "game-name";
    private static final UUID CONTEXT_USER_ID = UUID.randomUUID();
    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private StringEncryptor stringEncryptor;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private GameConverter underTest;

    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getUserId()).willReturn(CONTEXT_USER_ID);
        given(uuidConverter.convertDomain(CONTEXT_USER_ID)).willReturn(CONTEXT_USER_ID_STRING);
    }

    @Test
    public void convertEntity() {
        GameEntity entity = GameEntity.builder()
            .gameId(GAME_ID_STRING)
            .userId(USER_ID_STRING)
            .gameName(ENCRYPTED_GAME_NAME)
            .round(ROUND)
            .build();
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(uuidConverter.convertEntity(USER_ID_STRING)).willReturn(USER_ID);
        given(stringEncryptor.decryptEntity(ENCRYPTED_GAME_NAME, CONTEXT_USER_ID_STRING)).willReturn(GAME_NAME);

        Game result = underTest.convertEntity(entity);

        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getGameName()).isEqualTo(GAME_NAME);
        assertThat(result.getRound()).isEqualTo(ROUND);
    }

    @Test
    public void convertDomain() {
        Game game = Game.builder()
            .gameId(GAME_ID)
            .userId(USER_ID)
            .gameName(GAME_NAME)
            .round(ROUND)
            .build();

        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        given(stringEncryptor.encryptEntity(GAME_NAME, CONTEXT_USER_ID_STRING)).willReturn(ENCRYPTED_GAME_NAME);

        GameEntity result = underTest.convertDomain(game);

        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getUserId()).isEqualTo(USER_ID_STRING);
        assertThat(result.getGameName()).isEqualTo(ENCRYPTED_GAME_NAME);
        assertThat(result.getRound()).isEqualTo(ROUND);
    }
}