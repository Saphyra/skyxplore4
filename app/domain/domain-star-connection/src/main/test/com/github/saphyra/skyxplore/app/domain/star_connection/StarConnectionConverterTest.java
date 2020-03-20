package com.github.saphyra.skyxplore.app.domain.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class StarConnectionConverterTest {
    private static final String CONNECTION_ID_STRING = "connection-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final String STAR_ID_1_STRING = "star-id-1";
    private static final String STAR_ID_2_STRING = "star-id-2";
    private static final UUID CONNECTION_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID STAR_ID_1 = UUID.randomUUID();
    private static final UUID STAR_ID_2 = UUID.randomUUID();

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private StarConnectionConverter underTest;

    @Test
    public void convertEntity() {
        given(uuidConverter.convertEntity(CONNECTION_ID_STRING)).willReturn(CONNECTION_ID);
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(uuidConverter.convertEntity(STAR_ID_1_STRING)).willReturn(STAR_ID_1);
        given(uuidConverter.convertEntity(STAR_ID_2_STRING)).willReturn(STAR_ID_2);

        StarConnectionEntity entity = StarConnectionEntity.builder()
            .connectionId(CONNECTION_ID_STRING)
            .gameId(GAME_ID_STRING)
            .star1(STAR_ID_1_STRING)
            .star2(STAR_ID_2_STRING)
            .isNew(true)
            .build();

        StarConnection result = underTest.convertEntity(entity);

        assertThat(result.getConnectionId()).isEqualTo(CONNECTION_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getStar1()).isEqualTo(STAR_ID_1);
        assertThat(result.getStar2()).isEqualTo(STAR_ID_2);
        assertThat(result.isNew()).isFalse();
    }

    @Test
    public void convertDomain() {
        given(uuidConverter.convertDomain(CONNECTION_ID)).willReturn(CONNECTION_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(uuidConverter.convertDomain(STAR_ID_1)).willReturn(STAR_ID_1_STRING);
        given(uuidConverter.convertDomain(STAR_ID_2)).willReturn(STAR_ID_2_STRING);

        StarConnection domain = StarConnection.builder()
            .connectionId(CONNECTION_ID)
            .gameId(GAME_ID)
            .star1(STAR_ID_1)
            .star2(STAR_ID_2)
            .isNew(true)
            .build();

        StarConnectionEntity result = underTest.convertDomain(domain);

        assertThat(result.getConnectionId()).isEqualTo(CONNECTION_ID_STRING);
        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getStar1()).isEqualTo(STAR_ID_1_STRING);
        assertThat(result.getStar2()).isEqualTo(STAR_ID_2_STRING);
        assertThat(result.isNew()).isTrue();
    }
}