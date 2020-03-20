package com.github.saphyra.skyxplore.app.domain.star_connection;

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
public class StarConnectionDaoTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";

    @Mock
    private UuidConverter uuidConverter;

    @Mock
    private StarConnectionConverter converter;

    @Mock
    private StarConnectionRepository repository;

    @InjectMocks
    private StarConnectionDao underTest;

    @Mock
    private StarConnection connection;

    @Mock
    private StarConnectionEntity connectionEntity;

    @Test
    public void deleteByGameId() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        underTest.deleteByGameId(GAME_ID);

        verify(repository).deleteByGameId(GAME_ID_STRING);
    }

    @Test
    public void getByGameId() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(repository.getByGameId(GAME_ID_STRING)).willReturn(Arrays.asList(connectionEntity));
        given(converter.convertEntity(Arrays.asList(connectionEntity))).willReturn(Arrays.asList(connection));

        List<StarConnection> result = underTest.getByGameId(GAME_ID);

        assertThat(result).containsExactly(connection);
    }
}