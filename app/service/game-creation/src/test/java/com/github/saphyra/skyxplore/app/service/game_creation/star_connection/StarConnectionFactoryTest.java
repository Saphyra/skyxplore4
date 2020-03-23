package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class StarConnectionFactoryTest {
    private static final UUID CONNECTION_ID = UUID.randomUUID();
    private static final UUID STAR_ID_1 = UUID.randomUUID();
    private static final UUID STAR_ID_2 = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private StarConnectionFactory underTest;

    @Mock
    private Star star1;

    @Mock
    private Star star2;

    @Test
    public void create() {
        given(idGenerator.randomUUID()).willReturn(CONNECTION_ID);
        given(star1.getStarId()).willReturn(STAR_ID_1);
        given(star2.getStarId()).willReturn(STAR_ID_2);
        given(star1.getGameId()).willReturn(GAME_ID);

        StarConnection result = underTest.createConnection(star1, star2);

        assertThat(result.getConnectionId()).isEqualTo(CONNECTION_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getStar1()).isEqualTo(STAR_ID_1);
        assertThat(result.getStar2()).isEqualTo(STAR_ID_2);
    }
}